/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.List;

public class PropertyNamesDTO implements Serializable
{
    private static final long serialVersionUID = -2702549491180006820L;

    public PropertyNamesDTO()
    {
    }

    public PropertyNamesDTO(List<String> propertyNames)
    {
        _propertyNames = propertyNames;
    }

    public List<String> getPropertyNames()
    {
        return _propertyNames;
    }

    public void setPropertyNames(List<String> propertyNames)
    {
        _propertyNames = propertyNames;
    }

    private List<String> _propertyNames;
}