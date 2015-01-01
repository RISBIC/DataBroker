/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.xmlconfig;

public class Problem
{
    public Problem(String message)
    {
        _message = message;
    }

    public String getMessage()
    {
        return _message;
    }

    public String toString()
    {
        return _message;
    }

    private String _message;
}
