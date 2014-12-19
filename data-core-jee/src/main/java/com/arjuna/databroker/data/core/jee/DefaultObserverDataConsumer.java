/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;

public class DefaultObserverDataConsumer<T> implements ObserverDataConsumer<T>
{
    private static final Logger logger = Logger.getLogger(DefaultObserverDataConsumer.class.getName());

    public DefaultObserverDataConsumer(DataFlowNode dataFlowNode, String methodName, Class<T> dataClass)
    {
        logger.log(Level.FINE, "DefaultObserverDataConsumer: " + dataFlowNode + ", " + methodName + ", " + dataClass);

        _dataFlowNode = dataFlowNode;
        _methodName   = methodName;
        _dataClass    = dataClass;
    }

    @Override
    public DataFlowNode getDataFlowNode()
    {
        return _dataFlowNode;
    }

    @Override
    public void consume(ObservableDataProvider<T> dataProvider, T data)
    {
        try
        {
            getMethod(_dataFlowNode.getClass(),_methodName).invoke(_dataFlowNode, data);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem invoking consumer", throwable);
        }
    }

    private Method getMethod(Class<?> nodeClass, String nodeMethodName)
    {
        try
        {
            return nodeClass.getMethod(nodeMethodName, new Class[]{_dataClass});
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to find method \"" + nodeMethodName + "\"", throwable);

            return null;
        }
    }

    private final DataFlowNode _dataFlowNode;
    private final String       _methodName;
    private final Class<T>     _dataClass;
}
