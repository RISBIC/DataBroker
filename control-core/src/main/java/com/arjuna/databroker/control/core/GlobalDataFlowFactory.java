/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        List<String> metaPropertyNames = new LinkedList<String>();

        metaPropertyNames.add("Type");

        return metaPropertyNames;
    }

    @Override
    public List<String> getPropertyNames(Map<String, String> metaProperties)
        throws InvalidMetaPropertyException, MissingMetaPropertyException
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.getPropertyNames: " + metaProperties);

        String type = metaProperties.get("Type");
        if (type == null)
            throw new MissingMetaPropertyException("Expected property called \"Type\"", "Type");
        else if (type.equals("Standard"))
            return Collections.emptyList();
        else if (type.equalsIgnoreCase("3D"))
        {
            List<String> propertyNames = new LinkedList<String>();

            propertyNames.add("Height");
            propertyNames.add("Width");
            propertyNames.add("Depth");

            return propertyNames;
        }
        else
            throw new InvalidMetaPropertyException("Expected value for \"Type\": \"" + type + "\" expecting \"Standard\" or \"3D\"", "Type", type);
    }

    @Override
    public DataFlow createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.createDataFlow: " + name + ", " + metaProperties + ", " + properties);

        return new StandardDataFlow(name, properties);
    }
}