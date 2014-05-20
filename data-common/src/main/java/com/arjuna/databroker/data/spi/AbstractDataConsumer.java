/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.spi;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public abstract class AbstractDataConsumer<T> implements DataConsumer<T>
{
    public AbstractDataConsumer(DataFlowNode dataFlowNode)
    {
        _dataFlowNode  = dataFlowNode;
    }

    public DataFlowNode getDataFlowNode()
    {
        return _dataFlowNode;
    }

    public abstract void consume(DataProvider<T> dataProvider, T data);

    private DataFlowNode _dataFlowNode;
}
