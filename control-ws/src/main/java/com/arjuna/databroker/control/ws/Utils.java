/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.DataService;
import com.arjuna.databroker.data.DataSink;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.DataStore;

public class Utils
{
    public static final String DATASOURCE_TYPE    = "DataSource";
    public static final String DATASINK_TYPE      = "DataSink";
    public static final String DATAPROCESSOR_TYPE = "DataProcessor";
    public static final String DATASERVICE_TYPE   = "DataService";
    public static final String DATASTORE_TYPE     = "DataStore";

    public static String classToString(Class<? extends DataFlowNode> dataFlowNodeClass)
    {
        if (DataSource.class.isAssignableFrom(dataFlowNodeClass))
            return DATASOURCE_TYPE;
        else if (DataSink.class.isAssignableFrom(dataFlowNodeClass))
            return DATASINK_TYPE;
        else if (DataService.class.isAssignableFrom(dataFlowNodeClass))
            return DATASERVICE_TYPE;
        else if (DataStore.class.isAssignableFrom(dataFlowNodeClass))
            return DATASTORE_TYPE;
        else if (DataProcessor.class.isAssignableFrom(dataFlowNodeClass))
            return DATAPROCESSOR_TYPE;
        else
            return null;
    }

    public static Class<? extends DataFlowNode> stringToClass(String dataFlowNodeClassName)
    {
        if (DATASOURCE_TYPE.equals(dataFlowNodeClassName))
            return DataSource.class;
        else if (DATASINK_TYPE.equals(dataFlowNodeClassName))
            return DataSink.class;
        else if (DATASERVICE_TYPE.equals(dataFlowNodeClassName))
            return DataService.class;
        else if (DATASTORE_TYPE.equals(dataFlowNodeClassName))
            return DataStore.class;
        else if (DATAPROCESSOR_TYPE.equals(dataFlowNodeClassName))
            return DataProcessor.class;
        else
            return null;
    }
}