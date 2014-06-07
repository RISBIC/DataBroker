/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.core.nano;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.InvalidClassException;
import com.arjuna.databroker.data.InvalidMetaPropertyException;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingMetaPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;

public class Dummy01DataFlowNodeFactory implements DataFlowNodeFactory
{
    public Dummy01DataFlowNodeFactory(String name, Map<String, String> properties)
    {
        _name       = name;
        _properties = properties;
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
    public List<Class<? extends DataFlowNode>> getClasses()
    {
        return Collections.emptyList();
    }

    @Override
    public <T extends DataFlowNode> List<String> getMetaPropertyNames(Class<T> dataFlowNodeClass)
        throws InvalidClassException
    {
        return Collections.emptyList();
    }

    @Override
    public <T extends DataFlowNode> List<String> getPropertyNames(Class<T> dataFlowNodeClass, Map<String, String> metaProperties)
        throws InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException
    {
        return Collections.emptyList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends DataFlowNode> T createDataFlowNode(String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        if (dataFlowNodeClass.isAssignableFrom(Dummy01DataSource.class))
        {
            Timer             timer             = new Timer(true);
            Dummy01DataSource dummy01DataSource = new Dummy01DataSource(name, properties);
            timer.scheduleAtFixedRate(dummy01DataSource, 0, 1000);

            return (T) dummy01DataSource;
        }
        else
            return null;
    }

    private String              _name;
    private Map<String, String> _properties;
}
