/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

public class RequestProblemException extends Exception
{
    private static final long serialVersionUID = 8549367368816711378L;

    public RequestProblemException(String message)
    {
        super(message);
    }
}
