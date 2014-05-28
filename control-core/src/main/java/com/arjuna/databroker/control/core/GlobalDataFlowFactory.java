/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Singleton;

import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowFactory;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidMetaPropertyException;
import com.arjuna.databroker.data.MissingMetaPropertyException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;

@Singleton(name="DataFlowFactory")
public class GlobalDataFlowFactory implements DataFlowFactory
{
    private static final Logger logger = Logger.getLogger(GlobalDataFlowFactory.class.getName());

    public GlobalDataFlowFactory()
    {
    }

    @Override
    public String getName()
    {
        return "Default";
    }

    @Override
    public Map<String, String> getProperties()
    {
        return Collections.emptyMap();
    }

    @Override
    public List<String> getMetaPropertyNames()
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.getMetaPropertyNames");

        return Collections.emptyList();
    }

    @Override
    public List<String> getPropertyNames(Map<String, String> metaProperties)
        throws InvalidMetaPropertyException, MissingMetaPropertyException
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.getPropertyNames: " + metaProperties);

        if (metaProperties.isEmpty())
            return Collections.emptyList();
        else
        {
            Entry<String, String> entry = metaProperties.entrySet().iterator().next();

            throw new InvalidMetaPropertyException("Unexpected meta properties", entry.getKey(), entry.getValue());
        }
    }

    @Override
    public DataFlow createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.createDataFlow: " + name + ", " + metaProperties + ", " + properties);

        return new StandardDataFlow(name, properties);
    }
}