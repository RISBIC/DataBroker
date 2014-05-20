/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;

public class DataFlowLinkDTO implements Serializable
{
    private static final long serialVersionUID = 2409761182447148435L;

    public DataFlowLinkDTO()
    {
    }

    public DataFlowLinkDTO(String sourceDataFlowNodeName, String sinkDataFlowNodeName)
    {
        _sourceDataFlowNodeName = sourceDataFlowNodeName;
        _sinkDataFlowNodeName   = sinkDataFlowNodeName;
    }

    public String getSourceDataFlowNodeName()
    {
        return _sourceDataFlowNodeName;
    }

    public void setSourceDataFlowNodeName(String sourceDataFlowNodeName)
    {
        _sourceDataFlowNodeName = sourceDataFlowNodeName;
    }

    public String getSinkDataFlowNodeName()
    {
        return _sinkDataFlowNodeName;
    }

    public void setSinkDataFlowNodeName(String sinkDataFlowNodeName)
    {
        _sinkDataFlowNodeName = sinkDataFlowNodeName;
    }

    private String _sourceDataFlowNodeName;
    private String _sinkDataFlowNodeName;
}