/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Map;

/**
 * DataFlow is an interface through which the state of a data flow can be examined. 
 */
public interface DataFlow
{
    /**
     * Returns the name of the data flow.
     *
     * @return the name of the data flow
     */
    public String getName();

    /**
     * Returns the properties of the data flow.
     *
     * @return the properties of the data flow
     */
    public Map<String, String> getProperties();

    /**
     * Returns the data flow node inventory associated with the data flow.
     * <p>
     * The data flow node inventory contains all the data flow nodes associated the data flow.
     *
     * @return the data flow node inventory of the data flow
     */
    public DataFlowNodeInventory getDataFlowNodeInventory();

    /**
     * Returns the data flow node factory inventory associated with the data flow.
     * <p>
     * The data flow node factory inventory contains all the data flow node factories associated the data flow.
     *
     * @return the data flow node factory inventory of the data flow
     */
    public DataFlowNodeFactoryInventory getDataFlowNodeFactoryInventory();
}
