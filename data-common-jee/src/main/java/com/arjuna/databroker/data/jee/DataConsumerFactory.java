/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;
import com.arjuna.databroker.data.connector.ReferrerDataConsumer;
import org.risbic.transport.jms.JMSDataConsumerImpl;

public class DataConsumerFactory
{
    private static final Logger logger = Logger.getLogger(DataConsumerFactory.class.getName());

    public static <T> DataConsumer<T> createDataConsumer(DataFlowNode dataFlowNode, String methodName, Class<T> dataClass)
    {
        logger.log(Level.FINE, "createDataConsumer");

        return new DefaultObserverDataConsumer<T>(dataFlowNode, methodName, dataClass);
    }

    public static <T> ObserverDataConsumer<T> createObserverDataConsumer(DataFlowNode dataFlowNode, String methodName, Class<T> dataClass)
    {
        logger.log(Level.FINE, "createObserverDataConsumer");

        return new DefaultObserverDataConsumer<T>(dataFlowNode, methodName, dataClass);
    }

    public static <T> ReferrerDataConsumer<T> createReferrerDataConsumer(DataFlowNode dataFlowNode, String methodName, Class<T> dataClass) throws Exception
    {
        if (Serializable.class.isAssignableFrom(dataClass))
        {
           logger.log(Level.FINE, "createNamedDataProvider");
           return new JMSDataConsumerImpl(dataFlowNode, methodName);
        }
        throw new RuntimeException("Unsupported Data Class: " + dataClass);
    }
}
