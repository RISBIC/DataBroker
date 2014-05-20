/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataService is an interface to data service.
 */
public interface DataService extends DataFlowNode
{
    /**
     * Returns the data classes of the data consumers, of the data service.
     * 
     * @return the data classes of the data consumers, of the data service
     */
    public Collection<Class<?>> getDataConsumerDataClasses();

    /**
     * Returns the data consumer, of the specified data class, of the data service.
     * 
     * @param dataClass the required data class
     * @return the data consumer, of the specified data class, of the data service
     */
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass);

    /**
     * Returns the data classes of the data providers, of the data service.
     * 
     * @return the data classes of the data providers, of the data service
     */
    public Collection<Class<?>> getDataProviderDataClasses();

    /**
     * Returns the data provider, of the specified data class, of the data service.
     * 
     * @param dataClass the required data class
     * @return the data provider, of the specified data class, of the data service
     */
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass);
}
