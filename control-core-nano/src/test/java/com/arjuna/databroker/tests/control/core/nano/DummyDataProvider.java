/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.core.nano;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public class DummyDataProvider<T> implements DataProvider<T>
{
    public DummyDataProvider(DataFlowNode dataFlowNode)
    {
        _dataFlowNode  = dataFlowNode;
        _dataConsumers = new LinkedList<DataConsumer<T>>();
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        return _dataFlowNode;
    }

    @Override
    public Collection<DataConsumer<T>> getDataConsumers()
    {
        return Collections.unmodifiableList(_dataConsumers);
    }

    @Override
    public void addDataConsumer(DataConsumer<T> dataConsumer)
    {
        _dataConsumers.add(dataConsumer);
    }

    @Override
    public void removeDataConsumer(DataConsumer<T> dataConsumer)
    {
        _dataConsumers.remove(dataConsumer);
    }

    @Override
    public void produce(T data)
    {
        for (DataConsumer<T> dataConsumer: _dataConsumers)
            dataConsumer.consume(this, data);
    }

    private DataFlowNode          _dataFlowNode;
    private List<DataConsumer<T>> _dataConsumers;
}
