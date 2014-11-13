/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.connector;

import com.arjuna.databroker.data.DataProvider;

/**
 * NamedDataProvider is an interface for named data provider.
 */
public interface NamedDataProvider<T> extends DataProvider<T>
{
    /**
     * Gets name associated with the Named Data Provider.
     * 
     * @param nameClass the class of the name required
     *
     * @return name associated with the Named Data Provider
     */
    public <N> N getName(Class<N> nameClass);
}
