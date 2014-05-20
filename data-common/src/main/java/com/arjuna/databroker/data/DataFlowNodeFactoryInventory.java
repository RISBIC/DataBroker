/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataFlowNodeFactoryInventory is an interface through which an data flow node factory inventory can be accessed.
 */
public interface DataFlowNodeFactoryInventory
{
    /**
     * Returns the data flow node factories within the data flow node factory inventory.
     * 
     * @return the data flow node factories within the data flow node factory inventory
     */
    public Collection<DataFlowNodeFactory> getDataFlowNodeFactorys();

    /**
     * Returns a named data flow node factory, if it is within the data flow node factory inventory.
     * 
     * @param name the name of the desired data flow node factory
     * @return the data flow node factory
     */
    public DataFlowNodeFactory getDataFlowNodeFactory(String name);

    /**
     * Adds a data flow node factory to the data flow node factory inventory.
     * 
     * @param dataFlowNodeFactory the data flow node factory to be added to the node factory inventory
     */
    public void addDataFlowNodeFactory(DataFlowNodeFactory dataFlowNodeFactory);

    /**
     * Removes a named data flow node factory from the data flow node factory inventory.
     * 
     * @param name the name of the data flow node factory to be removed from the data flow node factory inventory
     * @return indicates if a data flow node factory was removed from the data flow node factory inventory
     */
    public boolean removeDataFlowNodeFactory(String name);

    /**
     * Removes a specified data flow node factory from the data flow node factory inventory.
     * 
     * @param dataFlowNodeFactory the data flow node factory to be removed from the data flow node factory inventory
     * @return indicates if a data flow node factory was removed from the data flow node factory inventory
     */
    public boolean removeDataFlowNodeFactory(DataFlowNodeFactory dataFlowNodeFactory);
}
