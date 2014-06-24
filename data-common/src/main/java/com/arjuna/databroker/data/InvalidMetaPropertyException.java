/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

public class InvalidMetaPropertyException extends Exception
{
    private static final long serialVersionUID = 3982639730236754146L;

    public InvalidMetaPropertyException(String reason, String propertyName, String propertyValue)
    {
        super(reason + ": name \"" + propertyName + "\", value \"" + propertyValue + "\"");

        _reason         = reason;
        _propertyName   = propertyName;
        _propertyValue  = propertyValue;
    }

    public String getReason()
    {
        return _reason;
    }

    public String getPropertyName()
    {
        return _propertyName;
    }

    public String getPropertyValue()
    {
        return _propertyValue;
    }

    private String _reason;
    private String _propertyName;
    private String _propertyValue;
}
