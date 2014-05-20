/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;

@Singleton(name="DataFlowInventory")
public class GlobalDataFlowInventory implements DataFlowInventory
{
    public GlobalDataFlowInventory()
    {
        _inventory = new HashMap<String, DataFlow>();
    }

    @Override
    public Collection<DataFlow> getDataFlows()
    {
        return Collections.unmodifiableCollection(_inventory.values());
    }

    @Override
    public DataFlow getDataFlow(String name)
    {
        return _inventory.get(name);
    }

    @Override
    public void addDataFlow(DataFlow dataFlow)
    {
        _inventory.put(dataFlow.getName(), dataFlow);
    }

    @Override
    public boolean removeDataFlow(String name)
    {
        return _inventory.remove(name) != null;
    }

    @Override
    public boolean removeDataFlow(DataFlow dataFlow)
    {
        return _inventory.remove(dataFlow.getName()) != null;
    }

    private Map<String, DataFlow> _inventory;
}
