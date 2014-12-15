/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNodeState;

public class DefaultDataFlowNodeState implements DataFlowNodeState
{
    private static final Logger logger = Logger.getLogger(DefaultDataFlowNodeState.class.getName());

    public DefaultDataFlowNodeState(UUID uuid)
    {
        logger.log(Level.FINE, "DefaultDataFlowNodeState: " + uuid);

        _uuid = uuid;
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

    private UUID         _uuid;
    private Serializable _state;
}
