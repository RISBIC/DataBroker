/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataSink;

public class Dummy01DataSink implements DataSink, DataDispatcher<String>
{
    public Dummy01DataSink(DataFlow dataFlow, String name, Map<String, String> properties)
    {
        _dataFlow     = dataFlow;
        _name         = name;
        _properties   = properties;
        _dataConsumer = new DummyDataConsumer<String>(this);
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
    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    @Override
    public void dispatch(String data)
    {
        System.out.println("Sink:   [" + data + "]");
    }

    @Override
    public Collection<Class<?>> getDataConsumerDataClasses()
    {
        Set<Class<?>> dataConsumerDataClasses = new HashSet<Class<?>>();

        dataConsumerDataClasses.add(String.class);

        return dataConsumerDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass)
    {
        if (dataClass.equals(String.class))
            return (DataConsumer<T>) _dataConsumer;
        else
            return null;
    }

    private DataFlow                  _dataFlow;
    private String                    _name;
    private Map<String, String>       _properties;
    private DummyDataConsumer<String> _dataConsumer;
}
