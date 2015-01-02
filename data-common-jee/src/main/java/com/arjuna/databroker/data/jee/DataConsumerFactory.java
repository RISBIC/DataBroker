/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.lang.reflect.Type;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;

public interface DataConsumerFactory
{
    public <T> DataConsumer<T> createDataConsumer(DataFlowNode dataFlowNode, String methodName, Type dataConsumerType)
        throws Exception;
}
