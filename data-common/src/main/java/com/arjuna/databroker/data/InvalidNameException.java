/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

public class InvalidNameException extends Exception
{
    private static final long serialVersionUID = 4783653816908443297L;

    public InvalidNameException(String reason, String name)
    {
        super(reason + ": name \"" + name + "\"");

        _reason = reason;
        _name   = name;
    }

    public String getReason()
    {
        return _reason;
    }

    public String getName()
    {
        return _name;
    }

    private String _reason;
    private String _name;
}
