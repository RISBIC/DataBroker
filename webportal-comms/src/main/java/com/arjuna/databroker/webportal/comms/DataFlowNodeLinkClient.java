/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
public class DataFlowNodeLinkClient
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLinkClient.class.getName());

    public Boolean createDataFlowNodeLink(String serviceRootURL, String dataFlowId, String sourceDataFlowNodeId, String sinkDataFlowNodeId)
    {
        logger.log(Level.FINE, "DataFlowNodeLinkClient.createDataFlowNodeLink: " + serviceRootURL + ", " + dataFlowId + ", " + sourceDataFlowNodeId + ", " + sinkDataFlowNodeId);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflownodelink/{dataflowid}");
            request.pathParameter("dataflowid", dataFlowId);
            request.queryParameter("sourcedataflownodeid", sourceDataFlowNodeId);
            request.queryParameter("sinkdataflownodeid", sinkDataFlowNodeId);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<Boolean> response = request.post(new GenericType<Boolean>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else
            {
                logger.log(Level.WARNING, "DataFlowNodeLinkClient.createDataFlowNodeLink: status = " + response.getStatus());

                return false;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'createDataFlowNodeLink'", throwable);

            return false;
        }
    }

    public boolean removeDataFlowNodeLink(String serviceRootURL, String dataFlowId, String sourceDataFlowNodeId, String sinkDataFlowNodeId)
    {
        logger.log(Level.FINE, "DataFlowNodeLinkClient.removeDataFlowNodeLink: " + serviceRootURL + ", " + dataFlowId + ", " + sourceDataFlowNodeId + ", " + sinkDataFlowNodeId);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflownodelink/{dataflowid}");
            request.pathParameter("dataflowid", dataFlowId);
            request.queryParameter("sourcedataflownodeid", sourceDataFlowNodeId);
            request.queryParameter("sinkdataflownodeid", sinkDataFlowNodeId);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<Boolean> response = request.delete(new GenericType<Boolean>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else
            {
                logger.log(Level.WARNING, "Problem with rest call for 'removeDataFlowNodeLink'");

                return false;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'removeDataFlowNodeLink'", throwable);

            return false;
        }
    }
}
