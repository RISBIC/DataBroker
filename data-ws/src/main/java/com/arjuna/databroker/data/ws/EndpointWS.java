/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.ws;

import java.net.HttpURLConnection;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNodeInventory;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataSource;

@Path("/endpoints")
@Stateless
public class EndpointWS
{
    private static final Logger logger = Logger.getLogger(EndpointWS.class.getName());

    @PUT
    @Path("{dataFlowId}/{dataSourceId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void receiveDataText(@PathParam("dataFlowId") String dataFlowId, @PathParam("dataSourceId") String dataSourceId, String data)
    {
        logger.fine("receiveDataText: " + dataFlowId + ", " + dataSourceId);
        if (_dataFlowInventory != null)
        {
            DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

            if (dataFlow != null)
            {
                DataFlowNodeInventory dataFlowNodeInventory = dataFlow.getDataFlowNodeInventory();

                if (dataFlowNodeInventory != null)
                {
                    DataSource dataSourceNode = dataFlowNodeInventory.getDataFlowNode(dataSourceId, DataSource.class);

                    if (dataSourceNode != null)
                    {
                        DataProvider<String> dataProvider = dataSourceNode.getDataProvider(String.class);

                        if (dataProvider != null)
                            dataProvider.produce(data);
                        else
                            throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
                    }
                    else
                        throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
                }
                else
                    throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
            else
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @PUT
    @Path("{dataFlowId}/{dataSourceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void receiveDataJSON(@PathParam("dataFlowId") String dataFlowId, @PathParam("dataSourceId") String dataSourceId, String data)
    {
        logger.warning("receiveDataJSON: " + dataFlowId + ", " + dataSourceId);
        if (_dataFlowInventory != null)
        {
            DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

            if (dataFlow != null)
            {
                DataFlowNodeInventory dataFlowNodeInventory = dataFlow.getDataFlowNodeInventory();

                if (dataFlowNodeInventory != null)
                {
                    DataSource dataSourceNode = dataFlowNodeInventory.getDataFlowNode(dataSourceId, DataSource.class);

                    if (dataSourceNode != null)
                    {
                        DataProvider<String> dataProvider = dataSourceNode.getDataProvider(String.class);

                        if (dataProvider != null)
                            dataProvider.produce(data);
                        else
                            throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
                    }
                    else
                        throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
                }
                else
                    throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
            else
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;
}