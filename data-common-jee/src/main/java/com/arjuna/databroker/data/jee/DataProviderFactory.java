/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;

public interface DataProviderFactory
{
    public <T> DataProvider<T> createDataProvider(DataFlowNode dataFlowNode)
        throws Exception;
}