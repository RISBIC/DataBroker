/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Singleton;

@Singleton(name="DataProviderFactoryInventory")
public class GlobalDataProviderFactoryInventory implements DataProviderFactoryInventory
{
    public GlobalDataProviderFactoryInventory()
    {
        _dataProviderFactories = new LinkedList<DataProviderFactory>();
    }

    public Collection<DataProviderFactory> getDataProviderFactories()
    {
        return Collections.unmodifiableList(_dataProviderFactories);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataProviderFactory> Collection<T> getDataProviderFactories(Class<T> dataProviderClass)
    {
        List<T> dataProviderFactories = new LinkedList<T>();

        for (DataProviderFactory dataProviderFactory: _dataProviderFactories)
            if (dataProviderClass.isAssignableFrom(dataProviderFactory.getClass()))
                dataProviderFactories.add((T) dataProviderFactory);

        return dataProviderFactories;
    }

    public boolean addDataProviderFactory(DataProviderFactory dataProviderFactory)
    {
        return _dataProviderFactories.add(dataProviderFactory);
    }

    public boolean removeDataProviderFactory(DataProviderFactory dataProviderFactory)
    {
        return _dataProviderFactories.remove(dataProviderFactory);
    }

    private List<DataProviderFactory> _dataProviderFactories;
}
