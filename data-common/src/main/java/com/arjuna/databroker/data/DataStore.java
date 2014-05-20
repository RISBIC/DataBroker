/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataStore is an interface to data store.
 */
public interface DataStore extends DataFlowNode
{
    /**
     * Returns the data classes of the data consumers, of the data store.
     * 
     * @return the data classes of the data consumers, of the data store
     */
    public Collection<Class<?>> getDataConsumerDataClasses();

    /**
     * Returns the data consumer, of the specified data class, of the data store.
     * 
     * @param dataClass the required data class
     * @return the data consumer, of the specified data class, of the data store
     */
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass);

    /**
     * Returns the data classes of the data providers, of the data store.
     * 
     * @return the data classes of the data providers, of the data store
     */
    public Collection<Class<?>> getDataProviderDataClasses();

    /**
     * Returns the data provider, of the specified data class, of the data store.
     * 
     * @param dataClass the required data class
     * @return the data provider, of the specified data class, of the data store
     */
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass);
}
