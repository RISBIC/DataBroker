/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.ws;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.spi.DefaultDataProvider;

public class EndpointDataSource implements DataSource
{
    public EndpointDataSource(String name, Map<String, String> properties)
    {
        _name             = name;
        _properties       = properties;
        _textDataProvider = new DefaultDataProvider<String>(this);
        _jsonDataProvider = new DefaultDataProvider<String>(this);
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return _properties;
    }

    @Override
    public Collection<Class<?>> getDataProviderDataClasses()
    {
        Set<Class<?>> dataProviderDataClasses = new HashSet<Class<?>>();

        dataProviderDataClasses.add(String.class);
        
        return dataProviderDataClasses;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass)
    {
        if (dataClass == String.class)
            return (DataProvider<T>) _textDataProvider;
        else if (dataClass == String.class)
            return (DataProvider<T>) _jsonDataProvider;
        else
            return null;
    }

    private String                      _name;
    private Map<String, String>         _properties;
    private DefaultDataProvider<String> _textDataProvider;
    private DefaultDataProvider<String> _jsonDataProvider;
}
