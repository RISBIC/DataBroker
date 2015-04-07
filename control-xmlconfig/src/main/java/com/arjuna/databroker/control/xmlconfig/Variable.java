/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.xmlconfig;

public class Variable
{
    public Variable()
    {
    }

    public Variable(String name, String label, String value)
    {
        _name  = name;
        _label = label;
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

    public String getLabel()
    {
        return _label;
    }

    public void setLabel(String label)
    {
        _label = label;
    }

    public String getValue()
    {
        return _value;
    }

    public void setValue(String value)
    {
        _value = value;
    }

    public String toString()
    {
        return "{" + _name + "," + _label + "," + _value + "}";
    }

    private String _name;
    private String _label;
    private String _value;
}
