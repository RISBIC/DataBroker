/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.connector;

import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.connector.ObservableDataProvider;

/**
 * ObserverDataConsumer is an interface to an observer data consumer.
 */
public interface ObserverDataConsumer<T> extends DataConsumer<T>
{
    /**
     * Cause the consuming of a data item by the associated data flow node.
     * 
     * @param dataProvider the data provider which has produced the data
     * @param data the data to be consumed
     */
    public void consume(ObservableDataProvider<T> dataProvider, T data);
}
