/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.control.comms.CreatePropertiesDTO;
import com.arjuna.databroker.control.comms.DataFlowDTO;
import com.arjuna.databroker.control.comms.DataFlowLinkDTO;
import com.arjuna.databroker.control.comms.DataFlowNodeDTO;
import com.arjuna.databroker.control.comms.DataFlowNodeFactoryDTO;
import com.arjuna.databroker.control.comms.FactoryNamesDTO;
import com.arjuna.databroker.control.comms.PropertiesDTO;
import com.arjuna.databroker.control.comms.PropertyNamesDTO;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.DataService;
import com.arjuna.databroker.data.DataSink;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.DataStore;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;

@Path("/dataflow")
@Stateless
public class DataFlowWS
{
    private static final Logger logger = Logger.getLogger(DataFlowWS.class.getName());

    @GET
    @Path("{dataflowid}/_dataflownodeclassnames")
    @Produces(MediaType.APPLICATION_JSON)
    public FactoryNamesDTO getDataFlowNodeClassNamesJSON(@PathParam("dataflowid") String dataFlowId)
    {
        logger.log(Level.INFO, "DataFlowWS.getDataFlowNodeClassNamesJSON: " + dataFlowId);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeFactoryInventory() != null))
                {
                    List<String> factoryNames = new LinkedList<String>();

                    for (DataFlowNodeFactory dataFlowNodeFactory: dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactorys())
                         factoryNames.add(dataFlowNodeFactory.getName());

                    return new FactoryNamesDTO(factoryNames);
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

    @GET
    @Path("{dataflowid}/_factorynames")
    @Produces(MediaType.APPLICATION_JSON)
    public FactoryNamesDTO getFactoryNamesJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("dataflownodeclassname") String dataFlowNodeClassName)
    {
        logger.log(Level.INFO, "DataFlowWS.getFactoryNamesJSON: " + dataFlowId + ", " + dataFlowNodeClassName);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeFactoryInventory() != null))
                {
                    List<String> factoryNames = new LinkedList<String>();

                    Class<? extends DataFlowNode> dataFlowNodeClass = Utils.stringToClass(dataFlowNodeClassName);
                    if (dataFlowNodeClass != null)
                        for (DataFlowNodeFactory dataFlowNodeFactory: dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactorys())
                            if (dataFlowNodeFactory.getClasses().contains(dataFlowNodeClass))
                                factoryNames.add(dataFlowNodeFactory.getName());

                    return new FactoryNamesDTO(factoryNames);
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

    @GET
    @Path("{dataflowid}/_metapropertynames")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyNamesDTO getMetaPropertyNamesJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("dataflownodeclassname") String dataFlowNodeClassName, @QueryParam("factoryname") String factoryName)
        throws InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.INFO, "DataFlowWS.getMetaPropertyNamesJSON: " + dataFlowId + ", " + dataFlowNodeClassName +  ", " + factoryName);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeFactoryInventory() !=  null))
                {
                    DataFlowNodeFactory dataFlowNodeFactory = dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactory(factoryName);
                    if (dataFlowNodeFactory != null)
                    {
                        try
                        {
                            List<String> metaPropertyNames = dataFlowNodeFactory.getMetaPropertyNames(Utils.stringToClass(dataFlowNodeClassName));

                            return new PropertyNamesDTO(metaPropertyNames);
                        }
                        catch (Throwable throwable)
                        {
                            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
                        }
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

    @POST
    @Path("{dataflowid}/_propertynames")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyNamesDTO getPropertyNamesJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("dataflownodeclassname") String dataFlowNodeClassName, @QueryParam("factoryname") String factoryName, PropertiesDTO metaProperties)
        throws InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.INFO, "DataFlowWS.getPropertyNamesJSON: " + dataFlowId + ", " + dataFlowNodeClassName +  ", " + factoryName + ", " + metaProperties);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeFactoryInventory() !=  null))
                {
                    DataFlowNodeFactory dataFlowNodeFactory = dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactory(factoryName);
                    if (dataFlowNodeFactory != null)
                    {
                        try
                        {
                            List<String> propertyNames = dataFlowNodeFactory.getPropertyNames(Utils.stringToClass(dataFlowNodeClassName), metaProperties.getProperties());

                            return new PropertyNamesDTO(propertyNames);
                        }
                        catch (Throwable throwable)
                        {
                            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
                        }
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

    @POST
    @Path("{dataflowid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createDataFlowNodeJSON(@PathParam("dataflowid") String dataFlowId, @QueryParam("dataflownodeclassname") String dataFlowNodeClassName, @QueryParam("factoryname") String factoryName, @QueryParam("name") String name, CreatePropertiesDTO createProperties)
        throws InvalidNameException, InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.INFO, "DataFlowWS.createDataFlowNodeJSON: " + dataFlowId + ", " + dataFlowNodeClassName + ", " + factoryName + ", " + name + ", " + createProperties);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeFactoryInventory() !=  null))
                {
                    DataFlowNodeFactory dataFlowNodeFactory = dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactory(factoryName);
                    if (dataFlowNodeFactory != null)
                    {
                        Class<? extends DataFlowNode> dataFlowNodeClass = Utils.stringToClass(dataFlowNodeClassName);
                        if (dataFlowNodeClass != null)
                        {
                            try
                            {
                                DataFlowNode dataFlowNode = dataFlowNodeFactory.createDataFlowNode(name, dataFlowNodeClass, createProperties.getMetaProperties(), createProperties.getProperties());                        
                                dataFlow.getDataFlowNodeInventory().addDataFlowNode(dataFlowNode);

                                return dataFlowNode.getName();
                            }
                            catch (Throwable throwable)
                            {
                                throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
                            }
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
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @GET
    @Path("{dataflowid}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataFlowDTO getDataFlowJSON(@PathParam("dataflowid") String dataFlowId)
    {
        logger.log(Level.FINE, "DataFlowWS.getDataFlowJSON: " + dataFlowId);

        DataFlowDTO dataFlowDTO = new DataFlowDTO(dataFlowId);
        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if (dataFlow != null)
                {
                    dataFlowDTO.setName(dataFlow.getName());
                    dataFlowDTO.setProperties(new PropertiesDTO(dataFlow.getProperties()));

                    List<DataFlowNodeDTO> dataFlowNodes = new LinkedList<DataFlowNodeDTO>();
                    for (DataFlowNode dataFlowNode: dataFlow.getDataFlowNodeInventory().getDataFlowNodes())
                        dataFlowNodes.add(new DataFlowNodeDTO(dataFlowNode.getName(), Utils.classToString(dataFlowNode.getClass()), dataFlowNode.getProperties()));
                    dataFlowDTO.setDataFlowNodes(dataFlowNodes);

                    List<DataFlowNodeFactoryDTO> dataFlowNodeFactories = new LinkedList<DataFlowNodeFactoryDTO>();
                    for (DataFlowNodeFactory dataFlowNodeFactory: dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactorys())
                    {
                        List<Class<? extends DataFlowNode>> dataFlowNodeFactoryClasses = dataFlowNodeFactory.getClasses();

                        Boolean dataSourceFactory    = dataFlowNodeFactoryClasses.contains(DataSource.class);
                        Boolean dataSinkFactory      = dataFlowNodeFactoryClasses.contains(DataSink.class);;
                        Boolean dataProcessorFactory = dataFlowNodeFactoryClasses.contains(DataProcessor.class);;
                        Boolean dataServiceFactory   = dataFlowNodeFactoryClasses.contains(DataService.class);;
                        Boolean dataStoreFactory     = dataFlowNodeFactoryClasses.contains(DataStore.class);;

                        dataFlowNodeFactories.add(new DataFlowNodeFactoryDTO(dataFlowNodeFactory.getName(), dataSourceFactory, dataSinkFactory, dataProcessorFactory, dataServiceFactory, dataStoreFactory));
                    }
                    dataFlowDTO.setDataFlowNodeFactories(dataFlowNodeFactories);
                    
                    List<DataFlowLinkDTO> dataFlowLinks = new LinkedList<DataFlowLinkDTO>();
                    for (DataFlowNode dataFlowNode: dataFlow.getDataFlowNodeInventory().getDataFlowNodes())
                    {
                        // TODO: Add Data Flow Nodes Links
                    }
                    dataFlowDTO.setDataFlowLinks(dataFlowLinks);
                }
                else
                    throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
            else
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);

        return dataFlowDTO;
    }

    @DELETE
    @Path("{dataflowid}/{dataflownodeid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean removeDataFlowNodeJSON(@PathParam("dataflowid") String dataFlowId, @PathParam("dataflownodeid") String dataflowNodeId)
    {
        logger.log(Level.FINE, "DataFlowWS.removeDataFlowNodeJSON: " + dataFlowId + ", " + dataflowNodeId);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);

                if ((dataFlow != null) && (dataFlow.getDataFlowNodeInventory() != null))
                    return dataFlow.getDataFlowNodeInventory().removeDataFlowNode(dataflowNodeId);
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