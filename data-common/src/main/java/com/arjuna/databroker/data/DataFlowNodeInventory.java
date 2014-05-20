/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Collection;

/**
 * DataFlowNodeInventory is an interface through which an data flow node inventory can be accessed.
 */
public interface DataFlowNodeInventory
{
    /**
     * Returns the data flow nodes within the data flow node inventory.
     * 
     * @return the data flow nodes within the data flow node inventory
     */
    public Collection<DataFlowNode> getDataFlowNodes();

    /**
     * Returns the data flow nodes within the data flow node inventory, of the specified data class
     * 
     * @param dataClass the required data class by the data flow node
     * @return the data flow nodes within the data flow node inventory, of the specified data class
     */
    public <T extends DataFlowNode> Collection<T> getDataFlowNodes(Class<T> dataClass);

    /**
     * Returns a named data flow node, if it is within the data flow node inventory.
     * 
     * @param name the name of the desired data flow node
     * @return the data flow node
     */
    public DataFlowNode getDataFlowNode(String name);

    /**
     * Returns a named data flow node, if supports a the specified class, if it is within the data flow node inventory.
     * 
     * @param name the name of the desired data flow node
     * @param dataClass the required data class by the data flow node
     * @return the data flow node
     */
    public <T extends DataFlowNode> T getDataFlowNode(String name, Class<T> dataClass);

    /**
     * Adds a data flow node to the data flow node inventory.
     * 
     * @param dataFlowNode the data flow node to be added to the node inventory
     */
    public void addDataFlowNode(DataFlowNode dataFlowNode);

    /**
     * Removes a named data flow node from the data flow node inventory.
     * 
     * @param name the name of the data flow node to be removed from the data flow node inventory
     * @return indicates if a data flow node was removed from the data flow node inventory
     */
    public boolean removeDataFlowNode(String name);

    /**
     * Removes a specified data flow node from the data flow node inventory.
     * 
     * @param dataFlowNode the data flow node to be removed from the data flow node inventory
     * @return indicates if a data flow node was removed from the data flow node inventory
     */
    public boolean removeDataFlowNode(DataFlowNode dataFlowNode);
}
