/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.io.Serializable;
import java.util.Map;

public class DataFlowNodeSummary implements Serializable
{
    private static final long serialVersionUID = -5710731910468133935L;

    public DataFlowNodeSummary()
    {
    }

    public DataFlowNodeSummary(String name, String type, Map<String, String> properties)
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
