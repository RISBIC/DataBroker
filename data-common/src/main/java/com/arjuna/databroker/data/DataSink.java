/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataSink is an interface to data sink.
 */
public interface DataSink extends DataFlowNode
{
    /**
     * Returns the data classes of the data consumers, of the data sink.
     * 
     * @return the data classes of the data consumers, of the data sink
     */
    public Collection<Class<?>> getDataConsumerDataClasses();

   /**
     * Returns the data consumer, of the specified data class, of the data sink.
     * 
     * @param dataClass the required data class
     * @return the data consumer, of the specified data class, of the data sink
     */
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass);
}
