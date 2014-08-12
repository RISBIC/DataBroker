/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

/**
 * DataProvider is an interface to data provider.
 */
public interface DataProvider<T>
{
    /**
     * Returns the data flow node associated with the data consumer.
     * 
     * @return the data flow node associated with the data consumer
     */
    public DataFlowNode getDataFlowNode();

    /**
     * Initiate the production of a data item by the associated data flow node.
     * 
     * @param data the data to be produced
     */
    public void produce(T data);
}
