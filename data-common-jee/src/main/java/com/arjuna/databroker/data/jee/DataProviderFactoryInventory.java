/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;

public interface DataProviderFactoryInventory
{
    public Collection<DataProviderFactory> getDataProviderFactories();

    public <T extends DataProviderFactory> Collection<T> getDataProviderFactories(Class<T> dataProviderClass);

    public boolean addDataProviderFactory(DataProviderFactory dataProviderFactory);

    public boolean removeDataProviderFactory(DataProviderFactory dataProviderFactory);
}