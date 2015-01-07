/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

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

    public static final String TYPE_METAPROPERYNAME           = "Type";
    public static final String STANDARD_TYPE_METAPROPERYVALUE = "Standard";
    public static final String DURABLE_TYPE_METAPROPERYVALUE  = "Durable";

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
        logger.log(Level.FINE, "GlobalDataFlowFactory.getProperties");

        return Collections.emptyMap();
    }

    @Override
    public List<String> getMetaPropertyNames()
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.getMetaPropertyNames");
        
        List<String> metaPropertyNames = new LinkedList<String>();

        metaPropertyNames.add(TYPE_METAPROPERYNAME);

        return metaPropertyNames;
    }

    @Override
    public List<String> getPropertyNames(Map<String, String> metaProperties)
        throws InvalidMetaPropertyException, MissingMetaPropertyException
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.getPropertyNames: " + metaProperties);

        if (metaProperties.isEmpty())
            throw new MissingMetaPropertyException("Missing '" + TYPE_METAPROPERYNAME + "' meta property", TYPE_METAPROPERYNAME);
        else if ((metaProperties.size() == 1) && (metaProperties.containsKey(TYPE_METAPROPERYNAME)))
            return Collections.emptyList();
        else
            throw new InvalidMetaPropertyException("Unexpected meta properties", null, null);
    }

    @Override
    public DataFlow createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.FINE, "GlobalDataFlowFactory.createDataFlow: " + name + ", " + metaProperties + ", " + properties);

        if ((metaProperties.size() == 1) && STANDARD_TYPE_METAPROPERYVALUE.equals(metaProperties.get(TYPE_METAPROPERYNAME)))
            return new StandardDataFlow(name, properties);
        else if ((metaProperties.size() == 1) && DURABLE_TYPE_METAPROPERYVALUE.equals(metaProperties.get(TYPE_METAPROPERYNAME)))
            return new DurableDataFlow(name, properties);
        else
            throw new InvalidMetaPropertyException("Expected value of 'Type' Standard' or 'Durable'", TYPE_METAPROPERYNAME, metaProperties.get(TYPE_METAPROPERYNAME));
    }
}