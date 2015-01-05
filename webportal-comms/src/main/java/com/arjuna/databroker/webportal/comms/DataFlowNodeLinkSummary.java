/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.io.Serializable;

public class DataFlowNodeLinkSummary implements Serializable
{
    private static final long serialVersionUID = 7778986720559309969L;

    public DataFlowNodeLinkSummary()
    {
    }

    public DataFlowNodeLinkSummary(String sourceDataFlowNodeName, String sinkDataFlowNodeName)
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

    public String toString()
    {
        return "(" + _sourceDataFlowNodeName + "->" + _sinkDataFlowNodeName + ")";
    }

    private String _sourceDataFlowNodeName;
    private String _sinkDataFlowNodeName;
}
