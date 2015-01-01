/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;
import com.arjuna.databroker.data.core.DataFlowLifeCycleControl;

@Singleton(name="DataFlowLifeCycleControl")
public class JEEDataFlowLifeCycleControl implements DataFlowLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(JEEDataFlowLifeCycleControl.class.getName());

    public <T extends DataFlow> T createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidPropertyException, MissingPropertyException
    {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try
        {
//            ClassLoader newClassLoader = dataFlowFactory.getClass().getClassLoader();
//            Thread.currentThread().setContextClassLoader(newClassLoader);

            return null;
        }
        finally
        {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    public Boolean removeDataFlow(DataFlow dataFlow)
    {
    	return false;
    }
}
