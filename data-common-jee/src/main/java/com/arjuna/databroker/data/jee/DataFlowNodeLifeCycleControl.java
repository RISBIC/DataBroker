/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataFlowNodeState;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.InvalidClassException;
import com.arjuna.databroker.data.InvalidMetaPropertyException;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingMetaPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;
import com.arjuna.databroker.data.connector.NamedDataProvider;
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;
import com.arjuna.databroker.data.connector.ReferrerDataConsumer;
import com.arjuna.databroker.data.jee.annotation.DataConsumerInjection;
import com.arjuna.databroker.data.jee.annotation.DataFlowNodeStateInjection;
import com.arjuna.databroker.data.jee.annotation.DataProviderInjection;
import com.arjuna.databroker.data.jee.annotation.PostActivated;
import com.arjuna.databroker.data.jee.annotation.PostConfig;
import com.arjuna.databroker.data.jee.annotation.PostCreated;
import com.arjuna.databroker.data.jee.annotation.PostDeactivated;
import com.arjuna.databroker.data.jee.annotation.PreActivated;
import com.arjuna.databroker.data.jee.annotation.PreConfig;
import com.arjuna.databroker.data.jee.annotation.PreDeactivated;
import com.arjuna.databroker.data.jee.annotation.PreDelete;

public class DataFlowNodeLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLifeCycleControl.class.getName());

    public static <T extends DataFlowNode> T createDataFlowNode(DataFlow dataFlow, DataFlowNodeFactory dataFlowNodeFactory, String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException
    {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try
        {
            ClassLoader newClassLoader = dataFlowNodeFactory.getClass().getClassLoader();
            Thread.currentThread().setContextClassLoader(newClassLoader);

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
        finally
        {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    public static Boolean processCreatedDataFlowNode(DataFlowNode dataFlowNode, DataFlow dataFlow)
    {
        injectDataConnectors(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostCreated.class);
        invokeLifeCycleOperation(dataFlowNode, PreActivated.class);

        if (dataFlow != null)
            dataFlow.getDataFlowNodeInventory().addDataFlowNode(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostActivated.class);

        return Boolean.TRUE;
    }

    public static void enterReconfigDataFlowNode(DataFlowNode dataFlowNode)
    {
        invokeLifeCycleOperation(dataFlowNode, PreDeactivated.class);
        invokeLifeCycleOperation(dataFlowNode, PostDeactivated.class);
        invokeLifeCycleOperation(dataFlowNode, PreConfig.class);
    }

    public static void exitReconfigDataFlowNode(DataFlowNode dataFlowNode)
    {
        invokeLifeCycleOperation(dataFlowNode, PostConfig.class);
        invokeLifeCycleOperation(dataFlowNode, PreActivated.class);
        invokeLifeCycleOperation(dataFlowNode, PostActivated.class);
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

    public static Boolean removeDataFlowNode(DataFlowNode dataFlowNode)
    {
        invokeLifeCycleOperation(dataFlowNode, PreDeactivated.class);

        Boolean result = Boolean.TRUE;
        if (dataFlowNode.getDataFlow() != null)
            result = dataFlowNode.getDataFlow().getDataFlowNodeInventory().removeDataFlowNode(dataFlowNode);

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
                            logger.log(Level.WARNING, "Life cycle operation \"" + method.getName() + "\" failed: " + throwable.toString());
                            if (logger.isLoggable(Level.FINE))
                                logger.log(Level.FINE, "Exception:", throwable);
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

        logger.log(Level.FINE, "injectDataConnectors class = \"" + dataFlowNodeClass + "\"");

        while (dataFlowNodeClass != null)
        {
            for (Field field: dataFlowNodeClass.getDeclaredFields())
            {
                if (field.isAnnotationPresent(DataConsumerInjection.class))
                {
                    try
                    {
                        logger.log(Level.FINE, "DataConsumerInjection \"" + field.getName() + "\", \"" + field.getType() + "\"");
                        DataConsumerInjection dataConsumerInjection = field.getAnnotation(DataConsumerInjection.class);
                        boolean accessable = field.isAccessible();
                        field.setAccessible(true);
                        if (field.getType().isAssignableFrom(DataConsumer.class))
                            field.set(dataFlowNode, DataConsumerFactory.createDataConsumer(dataFlowNode, dataConsumerInjection.methodName(), (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]));
                        else if (field.getType().isAssignableFrom(ReferrerDataConsumer.class))
                            field.set(dataFlowNode, DataConsumerFactory.createReferrerDataConsumer(dataFlowNode, dataConsumerInjection.methodName(), (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]));
                        else if (field.getType().isAssignableFrom(ObserverDataConsumer.class))
                            field.set(dataFlowNode, DataConsumerFactory.createObserverDataConsumer(dataFlowNode, dataConsumerInjection.methodName(), (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]));
                        else
                            logger.log(Level.WARNING, "DataConsumer injection failed, unsupported type: " + field.getType());
                        field.setAccessible(accessable);
                    }
                    catch (IllegalAccessException illegalAccessException)
                    {
                        logger.log(Level.WARNING, "DataConsumer injection failed of \"" + field.getName() + "\": " + illegalAccessException);
                    }
                    catch (Throwable throwable)
                    {
                        logger.log(Level.WARNING, "DataConsumer injection failed of \"" + field.getName(), throwable);
                    }
                }

                if (field.isAnnotationPresent(DataProviderInjection.class))
                {
                    try
                    {
                        logger.log(Level.FINE, "DataProviderInjection \"" + field.getName() + "\", \"" + field.getType() + "\"");
                        boolean accessable = field.isAccessible();
                        field.setAccessible(true);
                        if (field.getType().isAssignableFrom(DataProvider.class))
                            field.set(dataFlowNode, DataProviderFactory.createDataProvider(dataFlowNode));
                        else if (field.getType().isAssignableFrom(NamedDataProvider.class))
                            field.set(dataFlowNode, DataProviderFactory.createNamedDataProvider(dataFlowNode));
                        else if (field.getType().isAssignableFrom(ObservableDataProvider.class))
                            field.set(dataFlowNode, DataProviderFactory.createObservableDataProvider(dataFlowNode));
                        else
                            logger.log(Level.WARNING, "DataProvider injection failed, unsupported type: " + field.getType());
                        field.setAccessible(accessable);
                    }
                    catch (IllegalAccessException illegalAccessException)
                    {
                        logger.log(Level.WARNING, "DataProvider injection failed of \"" + field.getName() + "\": " + illegalAccessException);
                    }
                    catch (Throwable throwable)
                    {
                        logger.log(Level.WARNING, "DataProvider injection failed of \"" + field.getName(), throwable);
                    }
                }

                if (field.isAnnotationPresent(DataFlowNodeStateInjection.class))
                {
                    try
                    {
                        logger.log(Level.FINE, "DataFlowNodeStateInjection \"" + field.getName() + "\", \"" + field.getType() + "\"");
                        boolean accessable = field.isAccessible();
                        field.setAccessible(true);
                        if (field.getType().isAssignableFrom(DataFlowNodeState.class))
                        {
                            DataFlowNodeState dataFlowNodeState = new DefaultDataFlowNodeState(null);
                            field.set(dataFlowNode, dataFlowNodeState);
                        }
                        else
                            logger.log(Level.WARNING, "DataFlowNodeState injection failed, unsupported type: " + field.getType());
                        field.setAccessible(accessable);
                    }
                    catch (IllegalAccessException illegalAccessException)
                    {
                        logger.log(Level.WARNING, "DataFlowNodeState injection failed of \"" + field.getName() + "\": " + illegalAccessException);
                    }
                    catch (Throwable throwable)
                    {
                        logger.log(Level.WARNING, "DataFlowNodeState injection failed of \"" + field.getName(), throwable);
                    }
                }
            }

            dataFlowNodeClass = dataFlowNodeClass.getSuperclass();
        }
    }
}
