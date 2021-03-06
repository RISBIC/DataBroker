/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.connector;

import java.util.Collection;
import com.arjuna.databroker.data.DataConsumer;

/**
 * RefererDataConsumer is an interface to a referrer data consumer.
 */
public interface ReferrerDataConsumer<T> extends DataConsumer<T>
{
    /**
     * Gets name class associated with the Referrer Data Consumer.
     *
     * @return name associated with the Referrer Data Consumer
     */
    public Class<?> getNameClass();

    /**
     * Returns the named data providers associated with the data consumer.
     *
     * @return the named data providers associated with the data consumer
     */
    public Collection<NamedDataProvider<T>> getDataProviders();

    /**
     * Add named data provider to referrer data consumer.
     *
     * @param <N> template of name class
     * @param name of the named data provider
     */
    public <N> void addReferredTo(N name);

    /**
     * Remove named data provider from referrer data consumer.
     *
     * @param <N> template of name class
     * @param name of the named data provider
     */
    public <N> void removeReferredTo(N name);
}
