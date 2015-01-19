/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;

public class DataFlowNodeLinkVO implements Serializable
{
    private static final long serialVersionUID = -8747057966712002057L;

    public DataFlowNodeLinkVO()
    {
    }

    public DataFlowNodeLinkVO(String sourceDataFlowNodeName, String sinkDataFlowNodeName)
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
