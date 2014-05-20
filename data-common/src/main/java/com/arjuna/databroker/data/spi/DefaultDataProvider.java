/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.spi;

import java.util.List;
import java.util.LinkedList;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public class DefaultDataProvider<T> implements DataProvider<T>
{
    public DefaultDataProvider(DataFlowNode dataFlowNode)
    {
        _dataFlowNode  = dataFlowNode;
        _dataConsumers = new LinkedList<DataConsumer<T>>();
    }

    public DataFlowNode getDataFlowNode()
    {
        return _dataFlowNode;
    }

    public void addDataConsumer(DataConsumer<T> dataConsumer)
    {
        _dataConsumers.add(dataConsumer);
    }

    public void removeDataConsumer(DataConsumer<T> dataConsumer)
    {
        _dataConsumers.remove(dataConsumer);
    }

    public void produce(T data)
    {
        for (DataConsumer<T> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    private DataFlowNode          _dataFlowNode;
    private List<DataConsumer<T>> _dataConsumers;
}
