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
     * Returns the name of the data flow node.
     *
     * @return the name of the data flow node
     */
    public String getName();

    /**
     * Returns the properties of the data flow node.
     *
     * @return the properties of the data flow node
     */
    public Map<String, String> getProperties();
}
