/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Map;

/**
 * DataFlowNode is an interface through which the state of a data flow node can be examined. 
 */
public interface DataFlowNode
{
    /**
     * Returns the data flow associated with the data flow node.
     * 
     * @return the data flow associated with the data flow node
     */
    public DataFlow getDataFlow();

    /**
     * Sets the data flow associated with the data flow node.
     * 
     * @param dataFlow the data flow to be associated with the data flow node
     */
    public void setDataFlow(DataFlow dataFlow)
        throws InvalidDataFlowException;

    /**
     * Returns the name of the data flow node.
     *
     * @return the name of the data flow node
     */
    public String getName();

    /**
     * Sets the name of the data flow node.
     *
     * @param name the name of the data flow node
     */
    public void setName(String name)
        throws InvalidNameException;

    /**
     * Returns the properties of the data flow node.
     *
     * @return the properties of the data flow node
     */
    public Map<String, String> getProperties();

    /**
     * Sets the properties of the data flow node.
     *
     * @param properties the properties of the data flow node
     */
    public void setProperties(Map<String, String> properties)
        throws InvalidPropertyException, MissingPropertyException;
}
