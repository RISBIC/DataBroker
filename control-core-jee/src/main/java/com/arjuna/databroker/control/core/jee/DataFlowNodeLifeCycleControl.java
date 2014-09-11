/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core.jee;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
import com.arjuna.databroker.data.jee.annotation.DataConsumerInjection;
import com.arjuna.databroker.data.jee.annotation.DataProviderInjection;
import com.arjuna.databroker.data.jee.annotation.PostActivated;
import com.arjuna.databroker.data.jee.annotation.PostCreated;
import com.arjuna.databroker.data.jee.annotation.PostDeactivated;
import com.arjuna.databroker.data.jee.annotation.PreActivated;
import com.arjuna.databroker.data.jee.annotation.PreDeactivated;
import com.arjuna.databroker.data.jee.annotation.PreDelete;

public class DataFlowNodeLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLifeCycleControl.class.getName());

    public static <T extends DataFlowNode> T createDataFlowNode(DataFlow dataFlow, DataFlowNodeFactory dataFlowNodeFactory, String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        try
        {
            T dataFlowNode = dataFlowNodeFactory.createDataFlowNode(name, dataFlowNodeClass, metaProperties, properties);

            injectDataConnectors(dataFlowNode);

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

    public static Boolean removeDataFlowNode(DataFlow dataFlow, String name)
    {
        DataFlowNode dataFlowNode = dataFlow.getDataFlowNodeInventory().getDataFlowNode(name);

        invokeLifeCycleOperation(dataFlowNode, PreDeactivated.class);

        Boolean result = dataFlow.getDataFlowNodeInventory().removeDataFlowNode(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostDeactivated.class);
        invokeLifeCycleOperation(dataFlowNode, PreDelete.class);

        return result;
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
                    if (((method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) && (method.getGenericParameterTypes().length == 0) && method.getReturnType().equals(Void.TYPE))
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
                        if ((method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)
                            logger.log(Level.WARNING, "Operation \"" + method.getName() + "\" isn't accessible");
                        if (method.getGenericParameterTypes().length != 0)
                            logger.log(Level.WARNING, "Operation \"" + method.getName() + "\" expects parameters");
                        if (! method.getReturnType().equals(Void.TYPE))
                            logger.log(Level.WARNING, "Operation \"" + method.getName() + "\" non-void return");
                    }
                }
            }

            dataFlowNodeClass = dataFlowNodeClass.getSuperclass();
        }
    }

    private static void injectDataConnectors(DataFlowNode dataFlowNode)
    {
        Class<?> dataFlowNodeClass = dataFlowNode.getClass();

        while (dataFlowNodeClass != null)
        {
           	logger.log(Level.WARNING, "DataConsumerInjection class = \"" + dataFlowNodeClass + "\"");
            for (Field field: dataFlowNodeClass.getDeclaredFields())
            {
            	logger.log(Level.WARNING, "DataConsumerInjection name = \"" + field.getName() + "\"");
                if (field.isAnnotationPresent(DataConsumerInjection.class))
                	logger.log(Level.WARNING, "DataConsumerInjection \"" + field.getName() + "\", \"" + field.getType() + "\"");

                if (field.isAnnotationPresent(DataProviderInjection.class))
                	logger.log(Level.WARNING, "DataProviderInjection \"" + field.getName() + "\", \"" + field.getType() + "\"");
            }

            dataFlowNodeClass = dataFlowNodeClass.getSuperclass();
        }
    }
}
