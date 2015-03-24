/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core;

public class DataFlowNodeLinkManagementException extends Exception
{
    private static final long serialVersionUID = 1490128459720063052L;

    public DataFlowNodeLinkManagementException(String message)
    {
        super(message);
    }

    public DataFlowNodeLinkManagementException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
