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
        throws RequestProblemException
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
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
            {
                String message = response.getEntity(String.class);
                logger.log(Level.WARNING, "RequestProblem [" + message + "]");
                throw new RequestProblemException(message);
//                throw new RequestProblemException(response.getEntity(String.class));
            }
            else
            {
                logger.log(Level.WARNING, "DataFlowNodeLinkClient.createDataFlowNodeLink: status = " + response.getStatus());

                throw new RequestProblemException("Problem during request for link creation");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'createDataFlowNodeLink'", throwable);

            throw new RequestProblemException("Problem requesting of link creation");
        }
    }

    public boolean removeDataFlowNodeLink(String serviceRootURL, String dataFlowId, String sourceDataFlowNodeId, String sinkDataFlowNodeId)
        throws RequestProblemException
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
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "Problem with rest call for 'removeDataFlowNodeLink'");

                throw new RequestProblemException("Problem during request for link removal");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'removeDataFlowNodeLink'", throwable);

            throw new RequestProblemException("Problem requesting of link removal");
        }
    }
}
