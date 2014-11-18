/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;

public class MetadataItemVO implements Serializable
{
    private static final long serialVersionUID = -2561387687117008865L;
    private String               _name;
    private String               _value;
    private String               _type;
    private List<MetadataItemVO> _items;

    public MetadataItemVO()
    {
    }

    public MetadataItemVO(String name, String value, List<MetadataItemVO> items)
    {
        _name  = name;
        _value = value;
        _items = items;
        _type = "unknown";

        for (int i = 0; i < items.size(); i++) {
            MetadataItemVO element = items.get(i);

            if (element.getName().equals("Data Type - ")) {
                _type = element.getValue();
                _items.clear();
            }

        }
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

    public String getType()
    {
        return _type;
    }

    public void setType(String type)
    {
        _type = type;
    }

    public List<MetadataItemVO> getItems()
    {
        return _items;
    }

    public void setItems(List<MetadataItemVO> items)
    {
        _items = items;
    }
}
