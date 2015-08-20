/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
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
     * @throws IllegalStateException if data flow node is not in a state in which its data flow can be set
     * @throws InvalidDataFlowException if data flow is not valid
     */
    public void setDataFlow(DataFlow dataFlow)
        throws IllegalStateException, InvalidDataFlowException;

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
     * @throws IllegalStateException if data flow node is not in a state in which its name can be set
     * @throws InvalidNameException if the name is not valid
     */
    public void setName(String name)
        throws IllegalStateException, InvalidNameException;

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
     * @throws IllegalStateException if data flow node is not in a state in which its properties can be set
     * @throws InvalidPropertyException if a property is not valid
     * @throws MissingPropertyException if a property is missing
     */
    public void setProperties(Map<String, String> properties)
        throws IllegalStateException, InvalidPropertyException, MissingPropertyException;
}
