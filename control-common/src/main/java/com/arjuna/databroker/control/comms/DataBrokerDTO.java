/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.List;

public class DataBrokerDTO implements Serializable
{
    private static final long serialVersionUID = -8574491548512882483L;

    public DataBrokerDTO()
    {
    }

    public DataBrokerDTO(List<String> dataFlowNames, List<DataFlowNodeFactoryDTO> dataFlowNodeFactories)
    {
        _dataFlowNames         = dataFlowNames;
        _dataFlowNodeFactories = dataFlowNodeFactories;
    }

    public List<String> getDataFlowNames()
    {
        return _dataFlowNames;
    }

    public void setDataFlowNames(List<String> dataFlowNames)
    {
        _dataFlowNames = dataFlowNames;
    }

    public List<DataFlowNodeFactoryDTO> getDataFlowNodeFactories()
    {
        return _dataFlowNodeFactories;
    }

    public void setDataFlowNodeFactories(List<DataFlowNodeFactoryDTO> dataFlowNodeFactories)
    {
        _dataFlowNodeFactories = dataFlowNodeFactories;
    }

    private List<String>                 _dataFlowNames;
    private List<DataFlowNodeFactoryDTO> _dataFlowNodeFactories;
}