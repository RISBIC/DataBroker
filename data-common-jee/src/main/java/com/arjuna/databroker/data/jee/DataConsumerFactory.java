/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;

public interface DataConsumerFactory
{
    public <T> DataConsumer<T> createDataConsumer(DataFlowNode dataFlowNode, String methodName, Class<T> dataClass)
        throws Exception;
}
