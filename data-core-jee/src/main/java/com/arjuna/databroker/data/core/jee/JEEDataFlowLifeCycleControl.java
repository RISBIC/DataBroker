/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowFactory;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.InvalidMetaPropertyException;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingMetaPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;
import com.arjuna.databroker.data.core.DataFlowLifeCycleControl;
import com.arjuna.databroker.data.core.DataFlowNodeLifeCycleControl;
import com.arjuna.databroker.data.core.DataFlowNodeLinkLifeCycleControl;
import com.arjuna.databroker.data.jee.store.DataFlowEntity;
import com.arjuna.databroker.data.jee.store.DataFlowNodeEntity;
import com.arjuna.databroker.data.jee.store.DataFlowNodeLinkEntity;
import com.arjuna.databroker.data.jee.store.DataFlowUtils;

@Singleton(name="DataFlowLifeCycleControl")
public class JEEDataFlowLifeCycleControl implements DataFlowLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(JEEDataFlowLifeCycleControl.class.getName());

    @Override
    public void recreateDataFlows()
    {
        logger.log(Level.FINE, "recreateDataFlows");

        try
        {
            List<DataFlowEntity> dataFlowEntites = _dataFlowUtils.list();
            while (! dataFlowEntites.isEmpty())
            {
                logger.log(Level.FINER, "recreateDataFlows: start pass");
                List<DataFlowEntity> remainingDataFlow = new LinkedList<DataFlowEntity>();

                for (DataFlowEntity dataFlowEntity: dataFlowEntites)
                {
                    logger.log(Level.FINER, "recreateDataFlows: " + dataFlowEntity.getId() + ", " + dataFlowEntity.getName());

                    if (isRecreatableDataFlow(dataFlowEntity))
                    {
                        DataFlow dataFlow = recreateDataFlow(dataFlowEntity);
                        if (dataFlow != null)
                            _dataFlowInventory.addDataFlow(dataFlow);
                    }
                    else
                    {
                        logger.log(Level.FINER, "recreateDataFlows: postpone " + dataFlowEntity.getId() + ", " + dataFlowEntity.getName());
                        remainingDataFlow.add(dataFlowEntity);
                    }
                }

                dataFlowEntites = remainingDataFlow;

                if (! dataFlowEntites.isEmpty())
                {
                    logger.log(Level.FINER, "recreateDataFlows: start scan sleep");
                    Thread.sleep(10000);
                }
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlows: recreate failed", throwable);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends DataFlow> T createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        if (logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, "createDataFlow: " + name + ", " + metaProperties + ", " + properties);

        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();

        try
        {
            ClassLoader newClassLoader = _dataFlowFactory.getClass().getClassLoader();
            Thread.currentThread().setContextClassLoader(newClassLoader);

            DataFlow dataFlow = _dataFlowFactory.createDataFlow(name, metaProperties, properties);
            for (DataFlowNodeFactory dataFlowNodeFactory: _dataFlowNodeFactoryInventory.getDataFlowNodeFactorys())
                dataFlow.getDataFlowNodeFactoryInventory().addDataFlowNodeFactory(dataFlowNodeFactory);
            _dataFlowInventory.addDataFlow(dataFlow);

            List<Class<?>> dataFlowNodeFactoryClasses = new LinkedList<Class<?>>();
            for (DataFlowNodeFactory dataFlowNodeFactory: dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactorys())
                dataFlowNodeFactoryClasses.add(dataFlowNodeFactory.getClass());

            _dataFlowUtils.create(UUID.randomUUID().toString(), name, properties, dataFlow.getClass(), dataFlowNodeFactoryClasses);

            return (T) dataFlow;
        }
        finally
        {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    @Override
    public Boolean removeDataFlow(DataFlow dataFlow)
    {
        if (logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, "removeDataFlow: " + dataFlow.getName());

        if (dataFlow != null)
        {
            for (DataFlowNode dataFlowNode: dataFlow.getDataFlowNodeInventory().getDataFlowNodes())
                _dataFlowNodeLifeCycleControl.removeDataFlowNode(dataFlow, dataFlowNode.getName());

            _dataFlowUtils.remove(dataFlow.getName());

            return _dataFlowInventory.removeDataFlow(dataFlow);
        }
        else
            return false;
    }

    private boolean isRecreatableDataFlow(DataFlowEntity dataFlowEntity)
    {
        logger.log(Level.FINE, "isRecreatableDataFlow: " + dataFlowEntity.getId());

        try
        {
            boolean dataFlowRecreatable = true;
            for (String dataFlowNodeFactoryClassName: dataFlowEntity.getDataFlowNodeFactoryClassNames())
            {
                boolean found = false;
                for (DataFlowNodeFactory dataFlowNodeFactory: _dataFlowNodeFactoryInventory.getDataFlowNodeFactorys())
                    if (dataFlowNodeFactoryClassName.equals(dataFlowNodeFactory.getClass().getName()))
                        found = true;

                dataFlowRecreatable = dataFlowRecreatable && found;
            }

            return dataFlowRecreatable;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlow: Recreate failed - " + dataFlowEntity.getId(), throwable);
        }

        return false;
    }

    private DataFlow recreateDataFlow(DataFlowEntity dataFlowEntity)
    {
        logger.log(Level.FINE, "recreateDataFlow: " + dataFlowEntity.getId());

        try
        {
            Class<?>            dataFlowClass = Thread.currentThread().getContextClassLoader().loadClass(dataFlowEntity.getClassName());
            AbstractJEEDataFlow dataFlow      = (AbstractJEEDataFlow) dataFlowClass.newInstance();
            dataFlow.setName(dataFlowEntity.getName());
            dataFlow.setProperties(dataFlowEntity.getProperties());

            boolean dataFlowActivatable = true;
            for (String dataFlowNodeFactoryClassName: dataFlowEntity.getDataFlowNodeFactoryClassNames())
                for (DataFlowNodeFactory dataFlowNodeFactory: _dataFlowNodeFactoryInventory.getDataFlowNodeFactorys())
                    if (dataFlowNodeFactoryClassName.equals(dataFlowNodeFactory.getClass().getName()))
                        dataFlow.getDataFlowNodeFactoryInventory().addDataFlowNodeFactory(dataFlowNodeFactory);

            for (DataFlowNodeEntity dataFlowNodeEntity: dataFlowEntity.getDataFlowNodes())
            {
                DataFlowNode dataFlowNode = recreateDataFlowNode(dataFlowNodeEntity, dataFlow);
                if (dataFlowNode != null)
                    dataFlow.getDataFlowNodeInventory().addDataFlowNode(dataFlowNode);
                else
                    dataFlowActivatable = false;
            }

            for (DataFlowNodeLinkEntity dataFlowNodeLinkEntity: dataFlowEntity.getDataFlowNodeLinks())
                dataFlowActivatable = dataFlowActivatable && recreateDataFlowNodeLink(dataFlowNodeLinkEntity, dataFlow);

            if (dataFlowActivatable)
                for (DataFlowNode dataFlowNode: dataFlow.getDataFlowNodeInventory().getDataFlowNodes())
                    _dataFlowNodeLifeCycleControl.activateDataFlowNode(dataFlowNode);

            return dataFlow;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlow: Recreate failed - " + dataFlowEntity.getId(), throwable);
        }

        return null;
    }

    private DataFlowNode recreateDataFlowNode(DataFlowNodeEntity dataFlowNodeEntity, DataFlow dataFlow)
    {
        logger.log(Level.FINE, "recreateDataFlowNode: " + dataFlowNodeEntity.getId());

        try
        {
            Class<?> dataFlowNodeClass = null;
            Iterator<DataFlowNodeFactory> dataFlowNodeFactoryIterator = _dataFlowNodeFactoryInventory.getDataFlowNodeFactorys().iterator();
            while ((dataFlowNodeClass == null) && dataFlowNodeFactoryIterator.hasNext())
            {
                try
                {
                    DataFlowNodeFactory dataFlowNodeFactory = dataFlowNodeFactoryIterator.next();
                    dataFlowNodeClass = dataFlowNodeFactory.getClass().getClassLoader().loadClass(dataFlowNodeEntity.getClassName());
                }
                catch (ClassNotFoundException classNotFoundException)
                {
                }
            }

            if (dataFlowNodeClass != null)
            {
                DataFlowNode dataFlowNode = (DataFlowNode) dataFlowNodeClass.newInstance();
                dataFlowNode.setName(dataFlowNodeEntity.getName());
                dataFlowNode.setProperties(dataFlowNodeEntity.getProperties());
                dataFlowNode.setDataFlow(dataFlow);

                _dataFlowNodeLifeCycleControl.completeCreationDataFlowNode(dataFlowNodeEntity.getId(), dataFlowNode);

                return dataFlowNode;
            }
            else
            {
                logger.log(Level.WARNING, "recreateDataFlowNode: Class load failed - " + dataFlowNodeEntity.getId());

                return null;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlowNode: Recreate failed - " + dataFlowNodeEntity.getId(), throwable);
        }

        return null;
    }

    private Boolean recreateDataFlowNodeLink(DataFlowNodeLinkEntity dataFlowNodeEntity, DataFlow dataFlow)
    {
        try
        {
            DataFlowNode sourceDataFlowNode = dataFlow.getDataFlowNodeInventory().getDataFlowNode(dataFlowNodeEntity.getNodeSource().getName());
            DataFlowNode sinkDataFlowNode   = dataFlow.getDataFlowNodeInventory().getDataFlowNode(dataFlowNodeEntity.getNodeSink().getName());

            return _dataFlowNodeLinkLifeCycleControl.recreateDataFlowNodeLink(sourceDataFlowNode, sinkDataFlowNode, dataFlow);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlowNodeLink: Recreate failed - " + dataFlowNodeEntity.getId(), throwable);

            return false;
        }
    }

    @EJB(name="DataFlowFactory")
    private DataFlowFactory _dataFlowFactory;
    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;
    @EJB(name="DataFlowNodeFactoryInventory")
    private DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory;
    @EJB(name="DataFlowNodeLifeCycleControl")
    private DataFlowNodeLifeCycleControl _dataFlowNodeLifeCycleControl;
    @EJB(name="DataFlowNodeLinkLifeCycleControl")
    private DataFlowNodeLinkLifeCycleControl _dataFlowNodeLinkLifeCycleControl;
    @EJB(name="DataFlowUtils")
    private DataFlowUtils _dataFlowUtils;
}
