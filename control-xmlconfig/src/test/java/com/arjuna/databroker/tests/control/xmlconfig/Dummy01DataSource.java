/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataSource;

public class Dummy01DataSource extends TimerTask implements DataSource
{
    public Dummy01DataSource(DataFlow dataFlow, String name, Map<String, String> properties)
    {
        _dataFlow     = dataFlow;
        _name         = name;
        _properties   = properties;
        _dataProvider = new DummyDataProvider<String>(this);
    }

    @Override
    public DataFlow getDataFlow()
    {
        return _dataFlow;
    }

    @Override
    public void setDataFlow(DataFlow dataFlow)
    {
        _dataFlow = dataFlow;
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public void setName(String name)
    {
        _name = name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        return Collections.unmodifiableMap(_properties);
    }

    @Override
    public void run()
    {
        String data = UUID.randomUUID().toString();
        System.out.println("Source: [" + data + "]");
        _dataProvider.produce(data);
    }

    @Override
    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
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

    private DataFlow                  _dataFlow;
    private String                    _name;
    private Map<String, String>       _properties;
    private DummyDataProvider<String> _dataProvider;
}
