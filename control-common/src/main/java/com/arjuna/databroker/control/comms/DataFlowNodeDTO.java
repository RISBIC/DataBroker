/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.Map;

public class DataFlowNodeDTO implements Serializable
{
    private static final long serialVersionUID = 8465215413381633136L;

    public static final String DATASOURCE_TYPE    = "DataSource";
    public static final String DATASINK_TYPE      = "DataSink";
    public static final String DATAPROCESSOR_TYPE = "DataProcessor";
    public static final String DATASERVICE_TYPE   = "DataService";
    public static final String DATASTORE_TYPE     = "DataStore";

    public DataFlowNodeDTO()
    {
    }

    public DataFlowNodeDTO(String name, String type, Map<String, String> properties)
    {
        _name       = name;
        _type       = type;
        _properties = properties;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getType()
    {
        return _type;
    }

    public void setType(String type)
    {
        _type = type;
    }

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    private String              _name;
    private String              _type;
    private Map<String, String> _properties;
}