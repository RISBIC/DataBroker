/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

/**
 * DataConsumer is an interface to a data consumer.
 */
public interface DataConsumer<T>
{
    /**
     * Returns the data flow node associated with the data consumer.
     * 
     * @return the data flow node associated with the data consumer
     */
    public DataFlowNode getDataFlowNode();
}
