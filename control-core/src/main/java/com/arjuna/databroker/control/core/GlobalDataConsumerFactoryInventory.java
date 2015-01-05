/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.jee.DataConsumerFactory;
import com.arjuna.databroker.data.jee.DataConsumerFactoryInventory;

@Singleton(name="DataConsumerFactoryInventory")
public class GlobalDataConsumerFactoryInventory implements DataConsumerFactoryInventory
{
    private static final Logger logger = Logger.getLogger(GlobalDataConsumerFactoryInventory.class.getName());

    public GlobalDataConsumerFactoryInventory()
    {
        _dataConsumerFactories = new LinkedList<DataConsumerFactory>();
    }

    public Collection<DataConsumerFactory> getDataConsumerFactories()
    {
    	logger.log(Level.FINE, "GlobalDataConsumerFactoryInventory.getDataConsumerFactories");

        return Collections.unmodifiableList(_dataConsumerFactories);
    }

    public boolean addDataConsumerFactory(DataConsumerFactory dataConsumerFactory)
    {
    	logger.log(Level.FINE, "GlobalDataConsumerFactoryInventory.addDataConsumerFactory: " + dataConsumerFactory);

        return _dataConsumerFactories.add(dataConsumerFactory);
    }

    public boolean removeDataConsumerFactory(DataConsumerFactory dataConsumerFactory)
    {
    	logger.log(Level.FINE, "GlobalDataConsumerFactoryInventory.removeDataConsumerFactory: " + dataConsumerFactory);

        return _dataConsumerFactories.remove(dataConsumerFactory);
    }

    private List<DataConsumerFactory> _dataConsumerFactories;
}
