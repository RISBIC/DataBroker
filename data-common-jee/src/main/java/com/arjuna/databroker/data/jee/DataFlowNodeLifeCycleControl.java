/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataProvider;
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

@Singleton(name="DataFlowNodeLifeCycleControl")
public class DataFlowNodeLifeCycleControl
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLifeCycleControl.class.getName());

    public <T extends DataFlowNode> T createDataFlowNode(DataFlow dataFlow, DataFlowNodeFactory dataFlowNodeFactory, String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
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

    public Boolean processCreatedDataFlowNode(DataFlowNode dataFlowNode, DataFlow dataFlow)
    {
        injectDataConnectors(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostCreated.class);
        invokeLifeCycleOperation(dataFlowNode, PreActivated.class);

        if (dataFlow != null)
            dataFlow.getDataFlowNodeInventory().addDataFlowNode(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostActivated.class);

        return Boolean.TRUE;
    }

    public Boolean removeDataFlowNode(DataFlow dataFlow, String name)
    {
        DataFlowNode dataFlowNode = dataFlow.getDataFlowNodeInventory().getDataFlowNode(name);

        invokeLifeCycleOperation(dataFlowNode, PreDeactivated.class);

        Boolean result = dataFlow.getDataFlowNodeInventory().removeDataFlowNode(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostDeactivated.class);
        invokeLifeCycleOperation(dataFlowNode, PreDelete.class);

        return result;
    }

    public Boolean removeDataFlowNode(DataFlowNode dataFlowNode)
    {
        invokeLifeCycleOperation(dataFlowNode, PreDeactivated.class);

        Boolean result = Boolean.TRUE;
        if (dataFlowNode.getDataFlow() != null)
            result = dataFlowNode.getDataFlow().getDataFlowNodeInventory().removeDataFlowNode(dataFlowNode);

        invokeLifeCycleOperation(dataFlowNode, PostDeactivated.class);
        invokeLifeCycleOperation(dataFlowNode, PreDelete.class);

        return result;
    }

    private <A extends Annotation> void invokeLifeCycleOperation(DataFlowNode dataFlowNode, Class<A> annotation)
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

    private void injectDataConnectors(DataFlowNode dataFlowNode)
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

                        DataConsumer<?>                 dataConsumer                = null;
                        Collection<DataConsumerFactory> dataConsumerFactories       = (Collection<DataConsumerFactory>) _dataConsumerFactoryInventory.getDataConsumerFactories();
                        Iterator<DataConsumerFactory>   dataConsumerFactoryIterator = dataConsumerFactories.iterator();
                        while ((dataConsumer == null) && dataConsumerFactoryIterator.hasNext())
                            dataConsumer = dataConsumerFactoryIterator.next().createDataConsumer(dataFlowNode, dataConsumerInjection.methodName(), field.getGenericType());

                        if (dataConsumer != null)
                            field.set(dataFlowNode, dataConsumer);
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

                        DataProvider<?>                 dataProvider                = null;
                        Collection<DataProviderFactory> dataProviderFactories       = (Collection<DataProviderFactory>) _dataProviderFactoryInventory.getDataProviderFactories();
                        Iterator<DataProviderFactory>   dataProviderFactoryIterator = dataProviderFactories.iterator();
                        while ((dataProvider == null) && dataProviderFactoryIterator.hasNext())
                            dataProvider = dataProviderFactoryIterator.next().createDataProvider(dataFlowNode, field.getGenericType());

                        if (dataProvider != null)
                            field.set(dataFlowNode, dataProvider);
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
            }

            dataFlowNodeClass = dataFlowNodeClass.getSuperclass();
        }
    }

    @EJB(name="DataConsumerFactoryInventory")
    private static DataConsumerFactoryInventory _dataConsumerFactoryInventory;

    @EJB(name="DataProviderFactoryInventory")
    private static DataProviderFactoryInventory _dataProviderFactoryInventory;
}
