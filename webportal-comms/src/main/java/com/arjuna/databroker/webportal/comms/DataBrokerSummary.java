/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.io.Serializable;
import java.util.List;

public class DataBrokerSummary implements Serializable
{
    private static final long serialVersionUID = -8310881498220191365L;

    public DataBrokerSummary()
    {
    }

    public DataBrokerSummary(List<DataFlowSummary> dataFlowSummaries, List<DataFlowNodeFactorySummary> dataFlowNodeFactorySummaries)
    {
        _dataFlowSummaries            = dataFlowSummaries;
        _dataFlowNodeFactorySummaries = dataFlowNodeFactorySummaries;
    }

    public List<DataFlowSummary> getDataFlowSummaries()
    {
        return _dataFlowSummaries;
    }

    public void setDataFlowSummaries(List<DataFlowSummary> dataFlowSummaries)
    {
        _dataFlowSummaries = dataFlowSummaries;
    }

    public List<DataFlowNodeFactorySummary> getDataFlowNodeFactorySummaries()
    {
        return _dataFlowNodeFactorySummaries;
    }

    public void setDataFlowNodeFactorySummaries(List<DataFlowNodeFactorySummary> dataFlowNodeFactorySummaries)
    {
        _dataFlowNodeFactorySummaries = dataFlowNodeFactorySummaries;
    }

    private List<DataFlowSummary>            _dataFlowSummaries;
    private List<DataFlowNodeFactorySummary> _dataFlowNodeFactorySummaries;
}
