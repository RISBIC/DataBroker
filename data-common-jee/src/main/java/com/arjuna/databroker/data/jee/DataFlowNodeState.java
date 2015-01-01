/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.io.Serializable;

/**
 * DataFlowNodeState is an interface to a data flow node state.
 */
public interface DataFlowNodeState
{
    /**
     * Returns the state associated with the of data flow node.
     * 
     * @return the state associated with the of data flow node.
     */
    public Serializable getState();

    /**
     * Sets the state associated with the of data flow node.
     * 
     * @param state the state associated with the of data flow node.
     */
    public void setState(Serializable state);
}
