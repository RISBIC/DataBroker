/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core;

import java.util.Map;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.InvalidClassException;
import com.arjuna.databroker.data.InvalidMetaPropertyException;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingMetaPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;

public interface DataFlowNodeLifeCycleControl
{
    public <T extends DataFlowNode> T createDataFlowNode(DataFlow dataFlow, DataFlowNodeFactory dataFlowNodeFactory, String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException;

    public Boolean completeCreationDataFlowNode(String dataFlowNodeId, DataFlowNode dataFlowNode);

    public Boolean completeCreationAndActivateDataFlowNode(String dataFlowNodeId, DataFlowNode dataFlowNode, DataFlow dataFlow);

    public Boolean activateDataFlowNode(DataFlowNode dataFlowNode);

    public void enterReconfigDataFlowNode(DataFlowNode dataFlowNode);

    public void exitReconfigDataFlowNode(DataFlowNode dataFlowNode);

    public Boolean removeDataFlowNode(DataFlow dataFlow, String name);

    public Boolean removeDataFlowNode(DataFlowNode dataFlowNode);
}
