/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public class DummyDataConsumer<T> implements DataConsumer<T>
{
    public DummyDataConsumer(DataDispatcher<T> dataDispatcher)
    {
        _dataDispatcher = dataDispatcher;
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        return _dataDispatcher;
    }

    @Override
    public void consume(DataProvider<T> dataProvider, T data)
    {
        _dataDispatcher.dispatch(data);
    }

    private DataDispatcher<T> _dataDispatcher;
}