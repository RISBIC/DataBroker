/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataProcessor is an interface to data processor.
 */
public interface DataProcessor extends DataFlowNode
{
    /**
     * Returns the data classes of the data consumers, of the data processor.
     * 
     * @return the data classes of the data consumers, of the data processor
     */
    public Collection<Class<?>> getDataConsumerDataClasses();

    /**
     * Returns the data consumer, of the specified data class, of the data processor.
     * 
     * @param dataClass the required data class
     * @return the data consumer, of the specified data class, of the data processor
     */
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass);

    /**
     * Returns the data classes of the data providers, of the data processor.
     * 
     * @return the data classes of the data providers, of the data processor
     */
    public Collection<Class<?>> getDataProviderDataClasses();

    /**
     * Returns the data provider, of the specified data class, of the data processor.
     * 
     * @param dataClass the required data class
     * @return the data provider, of the specified data class, of the data processor
     */
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass);
}
