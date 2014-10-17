/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.connector.NamedDataProvider;
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import org.risbic.transport.jms.JMSDataProviderImpl;

public class DataProviderFactory
{
    private static final Logger logger = Logger.getLogger(DataProviderFactory.class.getName());

    public static <T> DataProvider<T> createDataProvider(DataFlowNode dataFlowNode)
    {
        logger.log(Level.FINE, "createDataProvider");

        return new DefaultObservableDataProvider<>(dataFlowNode);
    }

    public static <T> ObservableDataProvider<T> createObservableDataProvider(DataFlowNode dataFlowNode)
    {
        logger.log(Level.FINE, "createObservableDataProvider");

        return new DefaultObservableDataProvider<>(dataFlowNode);
    }

    public static <T> NamedDataProvider<T> createNamedDataProvider(DataFlowNode dataFlowNode)
    {
       logger.log(Level.FINE, "createNamedDataProvider");

       return new JMSDataProviderImpl();
    }
}
