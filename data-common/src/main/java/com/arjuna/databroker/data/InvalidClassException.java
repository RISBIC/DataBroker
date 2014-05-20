/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

public class InvalidClassException extends Exception
{
    private static final long serialVersionUID = -5586107644840362294L;

    public InvalidClassException(String reason, String className)
    {
        super(reason + ": class name \"" + className + "\"");

        _reason    = reason;
        _className = className;
    }

    public String getReason()
    {
        return _reason;
    }

    public String getClassName()
    {
        return _className;
    }

    private String _reason;
    private String _className;
}
