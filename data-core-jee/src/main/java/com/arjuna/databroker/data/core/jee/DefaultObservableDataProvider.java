/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;

public class DefaultObservableDataProvider<T> implements ObservableDataProvider<T>
{
    private static final Logger logger = Logger.getLogger(DefaultObservableDataProvider.class.getName());

    public DefaultObservableDataProvider(DataFlowNode dataFlowNode)
    {
        logger.log(Level.FINE, "DefaultObservableDataProvider: " + dataFlowNode);

        _dataFlowNode  = dataFlowNode;
        _dataConsumers = new LinkedList<ObserverDataConsumer<T>>();
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        return _dataFlowNode;
    }

    @Override
    public Collection<ObserverDataConsumer<T>> getDataConsumers()
    {
        return Collections.unmodifiableList(_dataConsumers);
    }

    @Override
    public void addDataConsumer(ObserverDataConsumer<T> dataConsumer)
    {
        _dataConsumers.add(dataConsumer);
    }

    @Override
    public void removeDataConsumer(ObserverDataConsumer<T> dataConsumer)
    {
        _dataConsumers.remove(dataConsumer);
    }

    @Override
    public void produce(T data)
    {
        for (ObserverDataConsumer<T> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    private DataFlowNode                  _dataFlowNode;
    private List<ObserverDataConsumer<T>> _dataConsumers;
}
