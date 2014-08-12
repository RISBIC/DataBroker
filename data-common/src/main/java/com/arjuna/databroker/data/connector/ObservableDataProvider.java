/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.connector;

import java.util.Collection;
import com.arjuna.databroker.data.DataProvider;

/**
 * ObservableDataProvider is an interface for observable data provider.
 */
public interface ObservableDataProvider<T> extends DataProvider<T>
{
    /**
     * Returns the observer data consumers associated with the data provider.
     * 
     * @return the observer data consumers associated with the data provider
     */
    public Collection<ObserverDataConsumer<T>> getDataConsumers();

    /**
     * Adds an observer data consumer to the list of data consumer which will be notified when data is produced.
     * 
     * @param dataConsumer the observer data consumer which is to be added
     */
    public void addDataConsumer(ObserverDataConsumer<T> dataConsumer);

    /**
     * Removes an observer data consumer from the list of data consumer which will be notified when data is produced.
     * 
     * @param dataConsumer the observer data consumer which is to be removed
     */
    public void removeDataConsumer(ObserverDataConsumer<T> dataConsumer);
}
