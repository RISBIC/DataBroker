/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.Map;

public class DataFlowFactoryDTO implements Serializable
{
    private static final long serialVersionUID = -2388114213479378572L;

    public DataFlowFactoryDTO()
    {
    }

    public DataFlowFactoryDTO(String name, Map<String, String> properties)
    {
        _name       = name;
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

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    private String              _name;
    private Map<String, String> _properties;}
