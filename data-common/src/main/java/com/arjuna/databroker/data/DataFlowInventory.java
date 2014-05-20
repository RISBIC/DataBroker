/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataFlowInventory is an interface through which an data flow inventory can be accessed.
 */
public interface DataFlowInventory
{
    /**
     * Returns the data flows within the data flow inventory.
     * 
     * @return the data flows within the data flow inventory
     */
    public Collection<DataFlow> getDataFlows();

    /**
     * Returns a named data flow, if it is within the data flow inventory.
     * 
     * @param name the name of the desired data flow 
     * @return the data flow
     */
    public DataFlow getDataFlow(String name);

    /**
     * Adds a data flow to the data flow inventory.
     * 
     * @param dataFlow the data flow to be added to the inventory
     */
    public void addDataFlow(DataFlow dataFlow);

    /**
     * Removes a named data flow from the data flow inventory.
     * 
     * @param name the name of the data flow from be removed to the inventory
     * @return indicates if a data flow was removed from the data flow inventory
     */
    public boolean removeDataFlow(String name);

    /**
     * Removes a specified data flow from the data flow inventory.
     * 
     * @param dataFlow the data flow to be removed from the inventory
     * @return indicates if a data flow was removed from the data flow inventory
     */
    public boolean removeDataFlow(DataFlow dataFlow);
}
