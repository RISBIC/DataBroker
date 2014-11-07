/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Singleton;

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
