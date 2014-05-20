/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.net.HttpURLConnection;import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataService;
import com.arjuna.databroker.data.DataSink;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.DataStore;

@Path("/dataflownodelink")
@Stateless
public class DataFlowNodeLinkWS
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLinkWS.class.getName());

    @POST
    @Path("{dataflowid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public <T> String createDataFlowNodeLinkJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("sourcedataflownodeid") String sourceDataFlowNodeId, @QueryParam("sinkdataflownodeid") String sinkDataFlowNodeId)
    {
        logger.log(Level.INFO, "DataFlowLinkWS.createDataFlowNodeLinkJSON: " + dataFlowId + ", " + sourceDataFlowNodeId + ", " + sinkDataFlowNodeId);

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
                    {
                        @SuppressWarnings("unchecked")
                        Class<T> linkClass = (Class<T>) getLinkClass(sourceDataFlowNode, sinkDataFlowNode);

                        DataProvider<T> dataProvider = getSourceProvider(sourceDataFlowNode, linkClass);
                        DataConsumer<T> dataConsumer = getSinkConsumer(sourceDataFlowNode, linkClass);

                        if ((dataProvider != null) && (dataConsumer != null))
                            dataProvider.addDataConsumer(dataConsumer);
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
        
        return "";
    }

    @DELETE
    @Path("{dataflowid}")
    @Produces(MediaType.APPLICATION_JSON)
    public <T> Boolean removeDataFlowNodeLinkJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("sourcedataflownodeid") String sourceDataFlowNodeId, @QueryParam("sinkdataflownodeid") String sinkDataFlowNodeId)
    {
        logger.log(Level.FINE, "DataFlowWS.removeDataFlowNodeLinkJSON: " + dataFlowId + ", " + sourceDataFlowNodeId + ", " + sinkDataFlowNodeId);

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
                    {
                        @SuppressWarnings("unchecked")
                        Class<T> linkClass = (Class<T>) getLinkClass(sourceDataFlowNode, sinkDataFlowNode);

                        DataProvider<T> dataProvider = getSourceProvider(sourceDataFlowNode, linkClass);
                        DataConsumer<T> dataConsumer = getSinkConsumer(sourceDataFlowNode, linkClass);

                        if ((dataProvider != null) && (dataConsumer != null))
                            dataProvider.removeDataConsumer(dataConsumer);
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

        return true;
    }

    private Class<?> getLinkClass(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode)
    {
        Collection<Class<?>> sourceDataClasses = getSourceProviderClasses(sourceDataFlowNode);
        Collection<Class<?>> sinkDataClasses   = getSinkConsumerClasses(sinkDataFlowNode);
        
        if ((sourceDataClasses != null) && (sinkDataClasses != null))
        {
            for (Class<?> sourceDataClass: sourceDataClasses)
                for (Class<?> sinkDataClass: sinkDataClasses)
                    if (sourceDataClass.equals(sinkDataClass))
                        return sourceDataClass;

            return null;
        }
        else
            return null;
    }

    private Collection<Class<?>> getSourceProviderClasses(DataFlowNode sourceDataFlowNode)
    {
        if (sourceDataFlowNode instanceof DataSource)
            return ((DataSource) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataService)
            return ((DataService) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataStore)
            return ((DataStore) sourceDataFlowNode).getDataProviderDataClasses();
        else
            return Collections.emptySet();
    }

    private Collection<Class<?>> getSinkConsumerClasses(DataFlowNode sinkDataFlowNode)
    {
        if (sinkDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataService)
            return ((DataService) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataStore)
            return ((DataStore) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataSink)
            return ((DataSink) sinkDataFlowNode).getDataConsumerDataClasses();
        else
            return Collections.emptySet();
    }

    private <T> DataProvider<T> getSourceProvider(DataFlowNode sourceDataFlowNode, Class<T> dataClass)
    {
        if (sourceDataFlowNode instanceof DataSource)
            return ((DataSource) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataService)
            return ((DataService) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataStore)
            return ((DataStore) sourceDataFlowNode).getDataProvider(dataClass);
        else
            return null;
    }

    private <T> DataConsumer<T> getSinkConsumer(DataFlowNode sinkDataFlowNode, Class<T> dataClass)
    {
        if (sinkDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataService)
            return ((DataService) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataStore)
            return ((DataStore) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataSink)
            return ((DataSink) sinkDataFlowNode).getDataConsumer(dataClass);
        else
            return null;
    }

    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;
}