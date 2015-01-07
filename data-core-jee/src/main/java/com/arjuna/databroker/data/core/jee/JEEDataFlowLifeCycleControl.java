/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

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
import com.arjuna.databroker.data.jee.store.DataFlowEntity;
import com.arjuna.databroker.data.jee.store.DataFlowUtils;

@Singleton(name="DataFlowLifeCycleControl")
public class JEEDataFlowLifeCycleControl implements DataFlowLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(JEEDataFlowLifeCycleControl.class.getName());

    public void recreateDataFlows()
    {
        logger.log(Level.FINE, "recreateDataFlows");

        try
        {
            List<DataFlowEntity> dataFlowEntites = _dataFlowUtils.list();

            for (DataFlowEntity dataFlowEntity: dataFlowEntites)
            {
                logger.log(Level.FINER, "recreateDataFlows: " + dataFlowEntity.getId() + ", " + dataFlowEntity.getName());

                recreateDataFlow(dataFlowEntity);
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlows: recreate failed", throwable);
        }
    }

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

            _dataFlowUtils.create(UUID.randomUUID().toString(), name, properties, dataFlow.getClass());

            return (T) dataFlow;
        }
        finally
        {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

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

    public void recreateDataFlow(DataFlowEntity dataFlowEntity)
    {
        logger.log(Level.FINE, "recreateDataFlow: " + dataFlowEntity.getId());

        try
        {
            Class<?>            dataFlowClass = Thread.currentThread().getContextClassLoader().loadClass(dataFlowEntity.getClassName());
            AbstractJEEDataFlow dataFlow      = (AbstractJEEDataFlow) dataFlowClass.newInstance();
            dataFlow.setName(dataFlowEntity.getName());
            dataFlow.setProperties(dataFlowEntity.getProperties());

            _dataFlowInventory.addDataFlow(dataFlow);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "recreateDataFlow: Recreate failed" + dataFlowEntity.getId(), throwable);
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
    @EJB(name="DataFlowUtils")
    private DataFlowUtils _dataFlowUtils;
}
