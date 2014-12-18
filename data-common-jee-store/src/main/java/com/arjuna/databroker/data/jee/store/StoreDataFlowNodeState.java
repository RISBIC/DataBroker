/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import com.arjuna.databroker.data.jee.DataFlowNodeState;;

public class StoreDataFlowNodeState implements DataFlowNodeState
{
    private static final Logger logger = Logger.getLogger(StoreDataFlowNodeState.class.getName());

    public StoreDataFlowNodeState(String id)
    {
        logger.log(Level.FINE, "StoreDataFlowNodeState: " + id);

        try
        {
            _dataFlowNodeUtils = (DataFlowNodeUtils) new InitialContext().lookup("java:global/databroker/data-common-jee-store/DataFlowNodeUtils");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "StoreDataFlowNodeState: no dataFlowNodeUtils found", throwable);
        }

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

    private String            _id;
    private DataFlowNodeUtils _dataFlowNodeUtils;
}
