/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import com.arjuna.databroker.data.DataFlowNodeState;

public class StoreDataFlowNodeState implements DataFlowNodeState
{
    private static final Logger logger = Logger.getLogger(StoreDataFlowNodeState.class.getName());

    // TODO: Remove - for testing only
    public StoreDataFlowNodeState()
    {
        logger.log(Level.WARNING, "StoreDataFlowNodeState: for testing only");

        _id = UUID.randomUUID().toString();

        _dataFlowNodeUtils.create(_id, "Test", Collections.<String, String>emptyMap(), "The Node Class Name");
    }

    public StoreDataFlowNodeState(String name, Map<String, String> properties, String nodeClassName)
    {
        logger.log(Level.WARNING, "StoreDataFlowNodeState: " + name + ", " + properties + ", " + nodeClassName);

        _id = UUID.randomUUID().toString();

        _dataFlowNodeUtils.create(_id, name, properties, nodeClassName);
    }

    public StoreDataFlowNodeState(String id)
    {
        logger.log(Level.FINE, "StoreDataFlowNodeState: " + id);

        _id = id;
    }

    public String getId()
    {
        return _id;
    }

    @Override
    public Serializable getState()
    {
        return _dataFlowNodeUtils.getState(_id);
    }

    @Override
    public void setState(Serializable state)
    {
        _dataFlowNodeUtils.setState(_id, state);
    }

    private String _id;

    @EJB
    private DataFlowNodeUtils _dataFlowNodeUtils;
}
