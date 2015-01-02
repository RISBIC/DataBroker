/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.Collection;

public interface DataConsumerFactoryInventory
{
    public Collection<DataConsumerFactory> getDataConsumerFactories();

    public boolean addDataConsumerFactory(DataConsumerFactory dataConsumerFactory);

    public boolean removeDataConsumerFactory(DataConsumerFactory dataConsumerFactory);
}