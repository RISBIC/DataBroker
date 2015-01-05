/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.io.Serializable;
import java.util.Map;

import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.DataFlowNodeInventory;

public class DurableDataFlow implements DataFlow, Serializable
{
    private static final long serialVersionUID = -3016502556776725911L;

    public DurableDataFlow(String name, Map<String, String> properties)
    {
        _name                         = name;
        _properties                   = properties;
        _dataFlowNodeInventory        = new DurableDataFlowNodeInventory();
        _dataFlowNodeFactoryInventory = new DurableDataFlowNodeFactoryInventory();
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return _properties;
    }

    @Override
    public DataFlowNodeInventory getDataFlowNodeInventory()
    {
        return _dataFlowNodeInventory;
    }

    @Override
    public DataFlowNodeFactoryInventory getDataFlowNodeFactoryInventory()
    {
        return _dataFlowNodeFactoryInventory;
    }

    private String                       _name;
    private Map<String, String>          _properties;
    private DataFlowNodeInventory        _dataFlowNodeInventory;
    private DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory; 
}
