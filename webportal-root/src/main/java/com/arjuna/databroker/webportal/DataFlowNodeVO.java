/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Map;

public class DataFlowNodeVO implements Serializable
{
    private static final long serialVersionUID = -2269934804224020901L;

    public DataFlowNodeVO()
    {
    }

    public DataFlowNodeVO(String id, String name, String type, Map<String, String> properties)
    {
        _id         = id;
        _name       = name;
        _type       = type;
        _properties = properties;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
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

    private String              _id;
    private String              _name;
    private String              _type;
    private Map<String, String> _properties;
}
