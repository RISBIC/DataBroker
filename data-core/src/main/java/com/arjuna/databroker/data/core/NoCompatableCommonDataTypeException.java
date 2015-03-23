/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core;

public class NoCompatableCommonDataTypeException extends Exception
{
    private static final long serialVersionUID = 1433619460887117645L;

    public NoCompatableCommonDataTypeException(String message)
    {
        super(message);
    }

    public NoCompatableCommonDataTypeException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
