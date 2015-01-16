/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.core.DataFlowNodeLinkLifeCycleControl;
import com.arjuna.databroker.data.core.DataFlowNodeLinkManagementException;
import com.arjuna.databroker.data.core.NoCompatableCommonDataTransportTypeException;
import com.arjuna.databroker.data.core.NoCompatableCommonDataTypeException;

@Path("/dataflownodelink")
@Stateless
public class DataFlowNodeLinkWS
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLinkWS.class.getName());

    @POST
    @Path("{dataflowid}")
    @Produces(MediaType.APPLICATION_JSON)
    public <T> Boolean createDataFlowNodeLinkJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("sourcedataflownodeid") String sourceDataFlowNodeId, @QueryParam("sinkdataflownodeid") String sinkDataFlowNodeId)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException
    {
        logger.log(Level.FINE, "DataFlowNodeLinkWS.createDataFlowNodeLinkJSON: " + dataFlowId + ", " + sourceDataFlowNodeId + ", " + sinkDataFlowNodeId);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeInventory() !=  null))
                {
                    DataFlowNode sourceDataFlowNode = dataFlow.getDataFlowNodeInventory().getDataFlowNode(sourceDataFlowNodeId);
                    DataFlowNode sinkDataFlowNode   = dataFlow.getDataFlowNodeInventory().getDataFlowNode(sinkDataFlowNodeId);

                    if ((sourceDataFlowNode != null) && (sinkDataFlowNode != null))
                        return _dataFlowNodeLinkLifeCycleControl.createDataFlowNodeLink(sourceDataFlowNode, sinkDataFlowNode, dataFlow);
                    else
                        throw new DataFlowNodeLinkManagementException("Unable to find source or sink data flow nodes");
                }
                else
                    throw new DataFlowNodeLinkManagementException("Unable to find data flow");
            }
            else
                throw new DataFlowNodeLinkManagementException("Dataflow not specified");
        }
        else
            throw new DataFlowNodeLinkManagementException("No dataFlowInventory");
    }

    @DELETE
    @Path("{dataflowid}")
    @Produces(MediaType.APPLICATION_JSON)
    public <T> Boolean removeDataFlowNodeLinkJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("sourcedataflownodeid") String sourceDataFlowNodeId, @QueryParam("sinkdataflownodeid") String sinkDataFlowNodeId)
        throws DataFlowNodeLinkManagementException, NoCompatableCommonDataTypeException, NoCompatableCommonDataTransportTypeException
    {
        logger.log(Level.FINE, "DataFlowNodeLinkWS.removeDataFlowNodeLinkJSON: " + dataFlowId + ", " + sourceDataFlowNodeId + ", " + sinkDataFlowNodeId);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeInventory() !=  null))
                {
                    DataFlowNode sourceDataFlowNode = dataFlow.getDataFlowNodeInventory().getDataFlowNode(sourceDataFlowNodeId);
                    DataFlowNode sinkDataFlowNode   = dataFlow.getDataFlowNodeInventory().getDataFlowNode(sinkDataFlowNodeId);

                    if ((sourceDataFlowNode != null) && (sinkDataFlowNode != null))
                        return _dataFlowNodeLinkLifeCycleControl.removeDataFlowNodeLink(sourceDataFlowNode, sinkDataFlowNode, dataFlow);
                    else
                        throw new DataFlowNodeLinkManagementException("Unable to find source or sink data flow nodes");
                }
                else
                    throw new DataFlowNodeLinkManagementException("Unable to find data flow");
            }
            else
                throw new DataFlowNodeLinkManagementException("Dataflow not specified");
        }
        else
            throw new DataFlowNodeLinkManagementException("No dataFlowInventory");
    }

    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;
    @EJB(name="DataFlowNodeLinkLifeCycleControl")
    private DataFlowNodeLinkLifeCycleControl _dataFlowNodeLinkLifeCycleControl;
}
