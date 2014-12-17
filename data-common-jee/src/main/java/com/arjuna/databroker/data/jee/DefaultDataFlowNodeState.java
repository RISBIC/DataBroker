/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNodeState;

public class DefaultDataFlowNodeState implements DataFlowNodeState
{
    private static final Logger logger = Logger.getLogger(DefaultDataFlowNodeState.class.getName());

    public DefaultDataFlowNodeState(String id)
    {
        logger.log(Level.FINE, "DefaultDataFlowNodeState: " + id);

        _id = id;
    }

    public String getId()
    {
        return _id;
    }

    @Override
    public Serializable getState()
    {
        return _state;
    }

    @Override
    public void setState(Serializable state)
    {
        _state = state;
    }

    private String       _id;
    private Serializable _state;
}
