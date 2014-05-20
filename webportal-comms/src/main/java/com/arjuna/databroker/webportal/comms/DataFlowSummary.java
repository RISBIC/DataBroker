/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.io.Serializable;

public class DataFlowSummary implements Serializable
{
    private static final long serialVersionUID = 4020025506840808805L;

    public DataFlowSummary()
    {
    }

    public DataFlowSummary(String name)
    {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    private String _name;
}
