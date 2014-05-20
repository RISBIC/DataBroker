/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataSource is an interface to data source.
 */
public interface DataSource extends DataFlowNode
{
    /**
     * Returns the data classes of the data providers, of the data source.
     * 
     * @return the data classes of the data providers, of the data source
     */
    public Collection<Class<?>> getDataProviderDataClasses();

    /**
     * Returns the data provider, of the specified data class, of the data source.
     * 
     * @param dataClass the required data class
     * @return the data provider, of the specified data class, of the data source
     */
    public <T> DataProvider<T> getDataProvider(Class<T> dataClass);
}
