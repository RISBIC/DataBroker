/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.List;

public class ClassNamesDTO implements Serializable
{
    private static final long serialVersionUID = -6096595561103561903L;

    public ClassNamesDTO()
    {
    }

    public ClassNamesDTO(List<String> classNames)
    {
        _classNames = classNames;
    }

    public List<String> getClassNames()
    {
        return _classNames;
    }

    public void setClassNames(List<String> classNames)
    {
        _classNames = classNames;
    }

    private List<String> _classNames;
}