/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;

public class MetadataItemVO implements Serializable
{
    private static final long serialVersionUID = -2561387687117008865L;

    public MetadataItemVO()
    {
    }

    public MetadataItemVO(String name, String value, List<MetadataItemVO> items)
    {
        _name  = name;
        _value = value;
        _items = items;
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

    public List<MetadataItemVO> getItems()
    {
        return _items;
    }

    public void setItems(List<MetadataItemVO> items)
    {
        _items = items;
    }

    private String               _name;
    private String               _value;
    private List<MetadataItemVO> _items;
}
