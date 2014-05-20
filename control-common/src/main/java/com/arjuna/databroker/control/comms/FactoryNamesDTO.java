/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.List;

public class FactoryNamesDTO implements Serializable
{
    private static final long serialVersionUID = 2001144409393716485L;

    public FactoryNamesDTO()
    {
    }

    public FactoryNamesDTO(List<String> factoryNames)
    {
        _factoryNames = factoryNames;
    }

    public List<String> getFactoryNames()
    {
        return _factoryNames;
    }

    public void setFactoryNames(List<String> factoryNames)
    {
        _factoryNames = factoryNames;
    }

    private List<String> _factoryNames;
}