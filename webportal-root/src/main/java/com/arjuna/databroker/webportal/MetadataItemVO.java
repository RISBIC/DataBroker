/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetadataItemVO implements Serializable
{
    private static final long serialVersionUID = -2561387687117008865L;

    public MetadataItemVO()
    {
        _name  = null;
        _value = null;
    }

    public MetadataItemVO(String name, String value)
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

    public static Map<String, String> toMap(List<MetadataItemVO> properties)
    {
        if (properties != null)
        {
            Map<String, String> map = new HashMap<String, String>();

            for (MetadataItemVO property: properties)
                map.put(property.getName(), property.getValue());

            return map;
        }
        else
            return null;
    }

    public static List<MetadataItemVO> fromMap(Map<String, String> properties)
    {
        if (properties != null)
        {
            List<MetadataItemVO> list = new LinkedList<MetadataItemVO>();

            for (Map.Entry<String, String> property: properties.entrySet())
                list.add(new MetadataItemVO(property.getKey(), property.getValue()));

            return list;
        }
        else
            return null;
    }

    private String _name;
    private String _value;
}
