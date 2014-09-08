/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core.jee;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.InvalidClassException;
import com.arjuna.databroker.data.InvalidMetaPropertyException;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingMetaPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;
import com.arjuna.databroker.data.jee.annotation.PostActivated;
import com.arjuna.databroker.data.jee.annotation.PostCreated;
import com.arjuna.databroker.data.jee.annotation.PreActivated;

public class DataFlowNodeLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLifeCycleControl.class.getName());

    public static <T extends DataFlowNode> T createDataFlowNode(DataFlow dataFlow, DataFlowNodeFactory dataFlowNodeFactory, String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        try
        {
            T dataFlowNode = dataFlowNodeFactory.createDataFlowNode(name, dataFlowNodeClass, metaProperties, properties);

            invokeLifeCycleOperation(dataFlowNode, PostCreated.class);

            invokeLifeCycleOperation(dataFlowNode, PreActivated.class);

            dataFlow.getDataFlowNodeInventory().addDataFlowNode(dataFlowNode);

            invokeLifeCycleOperation(dataFlowNode, PostActivated.class);

            return dataFlowNode;
        }
        catch (InvalidNameException invalidNameException)
        {
            throw invalidNameException;
        }
        catch (InvalidClassException invalidClassException)
        {
            throw invalidClassException;
        }
        catch (InvalidMetaPropertyException invalidMetaPropertyException)
        {
            throw invalidMetaPropertyException;
        }
        catch (MissingMetaPropertyException missingMetaPropertyException)
        {
            throw missingMetaPropertyException;
        }
        catch (InvalidPropertyException invalidPropertyException)
        {
            throw invalidPropertyException;
        }
        catch (MissingPropertyException missingPropertyException)
        {
            throw missingPropertyException;
        }
    }

    private static <A extends Annotation> void invokeLifeCycleOperation(DataFlowNode dataFlowNode, Class<A> annotation)
    {
        Class<?> dataFlowNodeClass = dataFlowNode.getClass();

        while (dataFlowNodeClass != null)
        {
            for (Method method: dataFlowNodeClass.getDeclaredMethods())
            {
                if (method.isAnnotationPresent(annotation))
                {
                    if (method.isAccessible() && (method.getGenericParameterTypes().length == 0) && method.getGenericReturnType().equals(Void.class))
                    {
                        try
                        {
                            method.invoke(dataFlowNode);
                        }
                        catch (Throwable throwable)
                        {
                            if (logger.isLoggable(Level.FINE))
                                logger.log(Level.FINE, "Life cycle operation \"" + method.getName() + "\" failed", throwable);
                            else
                                logger.log(Level.WARNING, "Life cycle operation \"" + method.getName() + "\" failed: " + throwable.toString());
                        }
                    }
                    else
                    {
                        if (! method.isAccessible())
                            logger.log(Level.WARNING, "Operation \"" + method.getName() + "\" isn't accessible");
                        if (method.getGenericParameterTypes().length != 0)
                            logger.log(Level.WARNING, "Operation \"" + method.getName() + "\" expects parameters");
                        if (! method.getGenericReturnType().equals(Void.class))
                            logger.log(Level.WARNING, "Operation \"" + method.getName() + "\" non-void return");
                    }
                }
                else
                    logger.log(Level.WARNING, "Not Operation \"" + method.getName() + "\"");
            }

            dataFlowNodeClass = dataFlowNodeClass.getSuperclass();
        }
    }
}
