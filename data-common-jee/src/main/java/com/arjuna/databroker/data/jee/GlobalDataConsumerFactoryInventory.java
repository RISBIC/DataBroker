/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Singleton;

@Singleton(name="DataConsumerFactoryInventory")
public class GlobalDataConsumerFactoryInventory implements DataConsumerFactoryInventory
{
    public GlobalDataConsumerFactoryInventory()
    {
        _dataConsumerFactories = new LinkedList<DataConsumerFactory>();
    }

    public Collection<DataConsumerFactory> getDataConsumerFactories()
    {
        return Collections.unmodifiableList(_dataConsumerFactories);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataConsumerFactory> Collection<T> getDataConsumerFactories(Class<T> dataConsumerClass)
    {
        List<T> dataConsumerFactories = new LinkedList<T>();

        for (DataConsumerFactory dataConsumerFactory: _dataConsumerFactories)
            if (dataConsumerClass.isAssignableFrom(dataConsumerFactory.getClass()))
                dataConsumerFactories.add((T) dataConsumerFactory);

        return dataConsumerFactories;
    }

    public boolean addDataConsumerFactory(DataConsumerFactory dataConsumerFactory)
    {
        return _dataConsumerFactories.add(dataConsumerFactory);
    }

    public boolean removeDataConsumerFactory(DataConsumerFactory dataConsumerFactory)
    {
        return _dataConsumerFactories.remove(dataConsumerFactory);
    }

    private List<DataConsumerFactory> _dataConsumerFactories;
}
