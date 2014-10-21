/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;

public interface DataConsumerFactoryInventory
{
    public Collection<DataConsumerFactory> getDataConsumerFactories();

    public <T extends DataConsumerFactory> Collection<T> getDataConsumerFactories(Class<T> dataConsumerClass);

    public boolean addDataConsumerFactory(DataConsumerFactory dataConsumerFactory);

    public boolean removeDataConsumerFactory(DataConsumerFactory dataConsumerFactory);
}