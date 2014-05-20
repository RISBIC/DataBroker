/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.util.Map;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.DataFlowNodeInventory;

public class StandardDataFlow implements DataFlow
{
    public StandardDataFlow(String name, Map<String, String> properties)
    {
        _name                         = name;
        _properties                   = properties;
        _dataFlowNodeInventory        = new StandardDataFlowNodeInventory();
        _dataFlowNodeFactoryInventory = new StandardDataFlowNodeFactoryInventory();
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
