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
     * Adds a data consumer to the list of data consumer which will be notified when data is produced.
     * 
     * @param dataConsumer the data consumer which is to be added
     */
    public void addDataConsumer(DataConsumer<T> dataConsumer);

    /**
     * Removes a data consumer from the list of data consumer which will be notified when data is produced.
     * 
     * @param dataConsumer the data consumer which is to be removed
     */
    public void removeDataConsumer(DataConsumer<T> dataConsumer);

    /**
     * Initiate the production of a data item by the associated data flow node.
     * 
     * @param data the data to be produced
     */
    public void produce(T data);
}
