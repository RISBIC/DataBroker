/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.Map;

public class PropertiesDTO implements Serializable
{
    private static final long serialVersionUID = -4823706453142980142L;

    public PropertiesDTO()
    {
    }

    public PropertiesDTO(Map<String, String> properties)
    {
        _properties = properties;
    }

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    private Map<String, String> _properties;
}