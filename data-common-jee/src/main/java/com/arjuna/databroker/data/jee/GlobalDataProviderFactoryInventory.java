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

@Singleton(name="DataProviderFactoryInventory")
public class GlobalDataProviderFactoryInventory implements DataProviderFactoryInventory
{
    private static final Logger logger = Logger.getLogger(GlobalDataProviderFactoryInventory.class.getName());

    public GlobalDataProviderFactoryInventory()
    {
        _dataProviderFactories = new LinkedList<DataProviderFactory>();
    }

    public Collection<DataProviderFactory> getDataProviderFactories()
    {
    	logger.log(Level.FINE, "GlobalDataProviderFactoryInventory.getDataProviderFactories");

    	return Collections.unmodifiableList(_dataProviderFactories);
    }

    public boolean addDataProviderFactory(DataProviderFactory dataProviderFactory)
    {
    	logger.log(Level.FINE, "GlobalDataProviderFactoryInventory.addDataProviderFactory: " + dataProviderFactory);

        return _dataProviderFactories.add(dataProviderFactory);
    }

    public boolean removeDataProviderFactory(DataProviderFactory dataProviderFactory)
    {
    	logger.log(Level.FINE, "GlobalDataProviderFactoryInventory.removeDataProviderFactory: " + dataProviderFactory);

        return _dataProviderFactories.remove(dataProviderFactory);
    }

    private List<DataProviderFactory> _dataProviderFactories;
}
