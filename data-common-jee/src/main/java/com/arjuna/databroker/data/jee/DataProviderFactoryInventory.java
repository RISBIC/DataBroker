/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;

public interface DataProviderFactoryInventory
{
    public Collection<DataProviderFactory> getDataProviderFactories();

    public boolean addDataProviderFactory(DataProviderFactory dataProviderFactory);

    public boolean removeDataProviderFactory(DataProviderFactory dataProviderFactory);
}