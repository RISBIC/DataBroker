/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

public class IllegalStateException extends Exception
{
    private static final long serialVersionUID = 5736742283676762185L;

    public IllegalStateException(String reason, String state)
    {
        super(reason + ": name \"" + state + "\"");

        _reason = reason;
        _state  = state;
    }

    public String getReason()
    {
        return _reason;
    }

    public String getState()
    {
        return _state;
    }

    private String _reason;
    private String _state;
}
