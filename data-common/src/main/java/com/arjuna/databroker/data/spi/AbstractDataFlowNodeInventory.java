/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeInventory;

public abstract class AbstractDataFlowNodeInventory implements DataFlowNodeInventory
{
    public AbstractDataFlowNodeInventory()
    {
        _inventory = new HashMap<String, DataFlowNode>();
    }

    public Collection<DataFlowNode> getDataFlowNodes()
    {
        return Collections.unmodifiableCollection(_inventory.values());
    }

    @SuppressWarnings("unchecked")
    public <T extends DataFlowNode> Collection<T> getDataFlowNodes(Class<T> nodeClass)
    {
        Collection<T> dataFlowNodes = new LinkedList<T>();

        for (DataFlowNode dataFlowNode: _inventory.values())
            if ((dataFlowNode != null) && nodeClass.isInstance(dataFlowNode))
                dataFlowNodes.add((T) dataFlowNode);

        return Collections.unmodifiableCollection(dataFlowNodes);
    }

    public DataFlowNode getDataFlowNode(String name)
    {
        return _inventory.get(name);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataFlowNode> T getDataFlowNode(String name, Class<T> nodeClass)
    {
        DataFlowNode dataFlowNode = _inventory.get(name);
        if ((dataFlowNode != null) && nodeClass.isInstance(dataFlowNode))
            return (T) dataFlowNode;
        else
            return null;
    }

    public void addDataFlowNode(DataFlowNode dataFlowNode)
    {
        _inventory.put(dataFlowNode.getName(), dataFlowNode);
    }

    public boolean removeDataFlowNode(String name)
    {
        return _inventory.remove(name) != null;
    }

    public boolean removeDataFlowNode(DataFlowNode dataFlowNode)
    {
        return _inventory.remove(dataFlowNode.getName()) != null;
    }

    private Map<String, DataFlowNode> _inventory;
}
