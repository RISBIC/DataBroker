/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core;

public class NoCompatableCommonDataTransportTypeException extends Exception
{
    private static final long serialVersionUID = 421179776218209645L;

    public NoCompatableCommonDataTransportTypeException()
    {
        super();
    }

    public NoCompatableCommonDataTransportTypeException(String message)
    {
        super(message);
    }

    public NoCompatableCommonDataTransportTypeException(Throwable throwable)
    {
        super(throwable);
    }

    public NoCompatableCommonDataTransportTypeException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
