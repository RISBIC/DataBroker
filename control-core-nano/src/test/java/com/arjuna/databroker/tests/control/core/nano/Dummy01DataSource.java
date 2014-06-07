/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.core.nano;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;

import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataSource;

public class Dummy01DataSource extends TimerTask implements DataSource
{
    public Dummy01DataSource(String name, Map<String, String> properties)
    {
        _name         = name;
        _properties   = properties;
        _dataProvider = new DummyDataProvider<String>(this);
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return Collections.unmodifiableMap(_properties);
    }

    @Override
    public void run()
    {
        _dataProvider.produce(UUID.randomUUID().toString());
    }

    @Override
    public Collection<Class<?>> getDataProviderDataClasses()
    {
        Set<Class<?>> dataProviderDataClasses = new HashSet<Class<?>>();

        dataProviderDataClasses.add(String.class);

        return dataProviderDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass)
    {
        if (dataClass.equals(String.class))
            return (DataProvider<T>) _dataProvider;
        else
            return null;
    }

    private String                    _name;
    private Map<String, String>       _properties;
    private DummyDataProvider<String> _dataProvider;
}
