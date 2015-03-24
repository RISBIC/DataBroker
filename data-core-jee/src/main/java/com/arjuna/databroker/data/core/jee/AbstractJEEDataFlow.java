/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.Map;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.DataFlowNodeInventory;

public abstract class AbstractJEEDataFlow implements DataFlow
{
    public AbstractJEEDataFlow(String name, Map<String, String> properties)
    {
        _name                         = name;
        _properties                   = properties;
        _dataFlowNodeInventory        = new JEEDataFlowNodeInventory();
        _dataFlowNodeFactoryInventory = new JEEDataFlowNodeFactoryInventory();
    }

    @Override
    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
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
