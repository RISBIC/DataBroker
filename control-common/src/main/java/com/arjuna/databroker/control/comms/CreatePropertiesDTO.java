/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.Map;

public class CreatePropertiesDTO implements Serializable
{
    private static final long serialVersionUID = 3580562736880706546L;

    public CreatePropertiesDTO()
    {
    }

    public CreatePropertiesDTO(Map<String, String> metaProperties, Map<String, String> properties)
    {
        _metaProperties = metaProperties;
        _properties     = properties;
    }

    public Map<String, String> getMetaProperties()
    {
        return _metaProperties;
    }

    public void setMetaProperties(Map<String, String> metaProperties)
    {
        _metaProperties = metaProperties;
    }

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    private Map<String, String> _metaProperties;
    private Map<String, String> _properties;
}