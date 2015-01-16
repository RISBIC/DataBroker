/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core;

import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNode;

public interface DataFlowNodeLinkLifeCycleControl
{
    public <T> Boolean createDataFlowNodeLink(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode, DataFlow dataFlow)
       throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException;

    public <T> Boolean recreateDataFlowNodeLink(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode, DataFlow dataFlow)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException;

    public <T> Boolean removeDataFlowNodeLink(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode, DataFlow dataFlow)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException;
}
