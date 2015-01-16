/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataService;
import com.arjuna.databroker.data.DataSink;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.DataStore;
import com.arjuna.databroker.data.connector.NamedDataProvider;
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;
import com.arjuna.databroker.data.connector.ReferrerDataConsumer;
import com.arjuna.databroker.data.core.DataFlowNodeLinkLifeCycleControl;
import com.arjuna.databroker.data.core.DataFlowNodeLinkManagementException;
import com.arjuna.databroker.data.core.NoCompatableCommonDataTransportTypeException;
import com.arjuna.databroker.data.core.NoCompatableCommonDataTypeException;
import com.arjuna.databroker.data.jee.store.DataFlowEntity;
import com.arjuna.databroker.data.jee.store.DataFlowNodeEntity;
import com.arjuna.databroker.data.jee.store.DataFlowNodeLinkUtils;
import com.arjuna.databroker.data.jee.store.DataFlowNodeUtils;
import com.arjuna.databroker.data.jee.store.DataFlowUtils;

@Singleton(name="DataFlowNodeLinkLifeCycleControl")
public class JEEDataFlowNodeLinkLifeCycleControl implements DataFlowNodeLinkLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(JEEDataFlowNodeLinkLifeCycleControl.class.getName());

    @Override
    public <T> Boolean createDataFlowNodeLink(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode, DataFlow dataFlow)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException
    {
        logger.log(Level.FINE, "createDataFlowNodeLink");

        @SuppressWarnings("unchecked")
        Class<T> linkClass = (Class<T>) getLinkClass(sourceDataFlowNode, sinkDataFlowNode);

        if (linkClass != null)
        {
            DataProvider<T> dataProvider = getSourceProvider(sourceDataFlowNode, linkClass);
            DataConsumer<T> dataConsumer = getSinkConsumer(sinkDataFlowNode, linkClass);
    
            if ((dataProvider != null) && (dataConsumer != null))
            {
                if ((dataProvider instanceof ObservableDataProvider) && (dataConsumer instanceof ObserverDataConsumer))
                {
                    ObservableDataProvider<T> observableDataProvider = (ObservableDataProvider<T>) dataProvider;
                    ObserverDataConsumer<T>   observerDataConsumer   = (ObserverDataConsumer<T>) dataConsumer;
    
                    observableDataProvider.addDataConsumer(observerDataConsumer);
                }
                else if ((dataProvider instanceof NamedDataProvider) && (dataConsumer instanceof ReferrerDataConsumer))
                {
                    NamedDataProvider<T>    namedDataProvider    = (NamedDataProvider<T>) dataProvider;
                    ReferrerDataConsumer<T> referrerDataConsumer = (ReferrerDataConsumer<T>) dataConsumer;
    
                    referrerDataConsumer.addReferredTo(namedDataProvider.getName(referrerDataConsumer.getNameClass()));
                }
                else
                    throw new NoCompatableCommonDataTransportTypeException("No common data transport for class " + linkClass.getName());
    
                DataFlowEntity     dataFlowEntity           = _dataFlowUtils.find(dataFlow.getName());
                DataFlowNodeEntity sourceDataFlowNodeEntity = _dataFlowNodeUtils.find(sourceDataFlowNode.getName(), dataFlowEntity);
                DataFlowNodeEntity sinkDataFlowNodeEntity   = _dataFlowNodeUtils.find(sinkDataFlowNode.getName(), dataFlowEntity);
                _dataFlowNodeLinkUtils.create(UUID.randomUUID().toString(), sourceDataFlowNodeEntity, sinkDataFlowNodeEntity, dataFlowEntity);
            }
            else
                throw new DataFlowNodeLinkManagementException("Unable to find data consumer/provider for class " + linkClass.getName());
        }
        else
            throw new NoCompatableCommonDataTypeException("No common data class");

        return true;
    }

    @Override
    public <T> Boolean recreateDataFlowNodeLink(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode, DataFlow dataFlow)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException
    {
        logger.log(Level.FINE, "recreateDataFlowNodeLink");

        @SuppressWarnings("unchecked")
        Class<T> linkClass = (Class<T>) getLinkClass(sourceDataFlowNode, sinkDataFlowNode);

        if (linkClass != null)
        {
            DataProvider<T> dataProvider = getSourceProvider(sourceDataFlowNode, linkClass);
            DataConsumer<T> dataConsumer = getSinkConsumer(sinkDataFlowNode, linkClass);

            if ((dataProvider != null) && (dataConsumer != null))
            {
                if ((dataProvider instanceof ObservableDataProvider) && (dataConsumer instanceof ObserverDataConsumer))
                {
                    ObservableDataProvider<T> observableDataProvider = (ObservableDataProvider<T>) dataProvider;
                    ObserverDataConsumer<T>   observerDataConsumer   = (ObserverDataConsumer<T>) dataConsumer;

                    observableDataProvider.addDataConsumer(observerDataConsumer);
                }
                else if ((dataProvider instanceof NamedDataProvider) && (dataConsumer instanceof ReferrerDataConsumer))
                {
                    NamedDataProvider<T>    namedDataProvider    = (NamedDataProvider<T>) dataProvider;
                    ReferrerDataConsumer<T> referrerDataConsumer = (ReferrerDataConsumer<T>) dataConsumer;

                    referrerDataConsumer.addReferredTo(namedDataProvider.getName(referrerDataConsumer.getNameClass()));
                }
                else
                    throw new NoCompatableCommonDataTransportTypeException("No common data transport for class " + linkClass.getName());
            }
            else
                throw new DataFlowNodeLinkManagementException("Unable to find data consumer/provider for class " + linkClass.getName());
        }
        else
            throw new NoCompatableCommonDataTypeException("No common data class");

        return true;
    }

    @Override
    public <T> Boolean removeDataFlowNodeLink(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode, DataFlow dataFlow)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException
    {
        logger.log(Level.FINE, "removeDataFlowNodeLink");

        @SuppressWarnings("unchecked")
        Class<T> linkClass = (Class<T>) getLinkClass(sourceDataFlowNode, sinkDataFlowNode);

        if (linkClass != null)
        {
            DataProvider<T> dataProvider = getSourceProvider(sourceDataFlowNode, linkClass);
            DataConsumer<T> dataConsumer = getSinkConsumer(sinkDataFlowNode, linkClass);

            if ((dataProvider != null) && (dataConsumer != null))
            {
                if ((dataProvider instanceof ObservableDataProvider) && (dataConsumer instanceof ObserverDataConsumer))
                {
                    ObservableDataProvider<T> observableDataProvider = (ObservableDataProvider<T>) dataProvider;
                    ObserverDataConsumer<T>   observerDataConsumer   = (ObserverDataConsumer<T>) dataConsumer;

                    observableDataProvider.removeDataConsumer(observerDataConsumer);
                }
                else if ((dataProvider instanceof NamedDataProvider) && (dataConsumer instanceof ReferrerDataConsumer))
                {
                    NamedDataProvider<?>    namedDataProvider    = (NamedDataProvider<?>) dataProvider;
                    ReferrerDataConsumer<?> referrerDataConsumer = (ReferrerDataConsumer<?>) dataConsumer;

                    referrerDataConsumer.removeReferredTo(namedDataProvider.getName(referrerDataConsumer.getNameClass()));
                }
                else
                    throw new NoCompatableCommonDataTransportTypeException("No common data transport for class " + linkClass.getName());

                DataFlowEntity     dataFlowEntity           = _dataFlowUtils.find(dataFlow.getName());
                DataFlowNodeEntity sourceDataFlowNodeEntity = _dataFlowNodeUtils.find(sourceDataFlowNode.getName(), dataFlowEntity);
                DataFlowNodeEntity sinkDataFlowNodeEntity   = _dataFlowNodeUtils.find(sinkDataFlowNode.getName(), dataFlowEntity);
                _dataFlowNodeLinkUtils.remove(sourceDataFlowNodeEntity, sinkDataFlowNodeEntity, dataFlowEntity);
            }
            else
                throw new DataFlowNodeLinkManagementException("Unable to find data consumer/provider for class " + linkClass.getName());
        }
        else
            throw new NoCompatableCommonDataTypeException("No common data class");

        return true;
    }

    private Class<?> getLinkClass(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode)
    {
        Collection<Class<?>> sourceDataClasses = getSourceProviderClasses(sourceDataFlowNode);
        Collection<Class<?>> sinkDataClasses   = getSinkConsumerClasses(sinkDataFlowNode);

        if ((sourceDataClasses != null) && (sinkDataClasses != null))
        {
            for (Class<?> sourceDataClass: sourceDataClasses)
                for (Class<?> sinkDataClass: sinkDataClasses)
                    if (sourceDataClass.equals(sinkDataClass))
                        return sourceDataClass;

            return null;
        }
        else
            return null;
    }

    private Collection<Class<?>> getSourceProviderClasses(DataFlowNode sourceDataFlowNode)
    {
        if (sourceDataFlowNode instanceof DataSource)
            return ((DataSource) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataService)
            return ((DataService) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataStore)
            return ((DataStore) sourceDataFlowNode).getDataProviderDataClasses();
        else
            return Collections.emptySet();
    }

    private Collection<Class<?>> getSinkConsumerClasses(DataFlowNode sinkDataFlowNode)
    {
        if (sinkDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataService)
            return ((DataService) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataStore)
            return ((DataStore) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataSink)
            return ((DataSink) sinkDataFlowNode).getDataConsumerDataClasses();
        else
            return Collections.emptySet();
    }

    private <T> DataProvider<T> getSourceProvider(DataFlowNode sourceDataFlowNode, Class<T> dataClass)
    {
        if (sourceDataFlowNode instanceof DataSource)
            return ((DataSource) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataService)
            return ((DataService) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataStore)
            return ((DataStore) sourceDataFlowNode).getDataProvider(dataClass);
        else
            return null;
    }

    private <T> DataConsumer<T> getSinkConsumer(DataFlowNode sinkDataFlowNode, Class<T> dataClass)
    {
        if (sinkDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataService)
            return ((DataService) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataStore)
            return ((DataStore) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataSink)
            return ((DataSink) sinkDataFlowNode).getDataConsumer(dataClass);
        else
            return null;
    }

    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;
    @EJB(name="DataFlowNodeFactoryInventory")
    private DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory;
    @EJB(name="DataFlowUtils")
    private DataFlowUtils _dataFlowUtils;
    @EJB(name="DataFlowNodeLUtils")
    private DataFlowNodeUtils _dataFlowNodeUtils;
    @EJB(name="DataFlowNodeLinkUtils")
    private DataFlowNodeLinkUtils _dataFlowNodeLinkUtils;
}
