/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PropertyVO implements Serializable
{
    private static final long serialVersionUID = -8952579347588817747L;

    public PropertyVO()
    {
        _name  = null;
        _value = null;
    }

    public PropertyVO(String name, String value)
    {
        _name  = name;
        _value = value;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getValue()
    {
        return _value;
    }

    public void setValue(String value)
    {
        _value = value;
    }

    public static Map<String, String> toMap(List<PropertyVO> properties)
    {
        if (properties != null)
        {
            Map<String, String> map = new HashMap<String, String>();

            for (PropertyVO property: properties)
                map.put(property.getName(), property.getValue());

            return map;
        }
        else
            return null;
    }

    public static List<PropertyVO> fromMap(Map<String, String> properties)
    {
        if (properties != null)
        {
            List<PropertyVO> list = new LinkedList<PropertyVO>();

            for (Map.Entry<String, String> property: properties.entrySet())
                list.add(new PropertyVO(property.getKey(), property.getValue()));

            return list;
        }
        else
            return null;
    }

    private String _name;
    private String _value;
}
