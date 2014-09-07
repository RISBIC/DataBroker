/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import com.arjuna.databroker.data.DataConsumer;

public class DataConsumerProducer
{
    private static final Logger logger = Logger.getLogger(DataConsumerProducer.class.getName());

    @Produces
    public <T> DataConsumer<T> getInstance(InjectionPoint injectionPoint)
    {
        logger.log(Level.WARNING, "getInstance: " + injectionPoint);

        return new DefaultObserverDataConsumer<T>(null, null, null); //TODO
    }
}