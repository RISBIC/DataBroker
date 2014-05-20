/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;

public abstract class AbstractDataFlowNodeFactoryInventory implements DataFlowNodeFactoryInventory
{
    private static final Logger logger = Logger.getLogger(AbstractDataFlowNodeFactoryInventory.class.getName());

    public AbstractDataFlowNodeFactoryInventory()
    {
        _inventory = new HashMap<String, DataFlowNodeFactory>();
    }

    public Collection<DataFlowNodeFactory> getDataFlowNodeFactorys()
    {
        return Collections.unmodifiableCollection(_inventory.values());
    }

    public DataFlowNodeFactory getDataFlowNodeFactory(String name)
    {
        return _inventory.get(name);
    }

    public void addDataFlowNodeFactory(DataFlowNodeFactory dataFlowNodeFactory)
    {
        logger.fine("addDataFlowNodeFactory: '" + dataFlowNodeFactory.getName() + "'");

        _inventory.put(dataFlowNodeFactory.getName(), dataFlowNodeFactory);
    }

    public boolean removeDataFlowNodeFactory(String name)
    {
        logger.fine("removeDataFlowNodeFactory: '" + name + "'");

        return _inventory.remove(name) != null;
    }

    public boolean removeDataFlowNodeFactory(DataFlowNodeFactory dataFlowNodeFactory)
    {
        logger.fine("removeDataFlowNodeFactory: '" + dataFlowNodeFactory.getName() + "'");

        return _inventory.remove(dataFlowNodeFactory.getName()) != null;
    }

    private Map<String, DataFlowNodeFactory> _inventory;
}
