/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;

public class DataFlowSummaryVO implements Serializable
{
    private static final long serialVersionUID = -2269934804224020901L;

    public DataFlowSummaryVO()
    {
    }

    public DataFlowSummaryVO(String name)
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
