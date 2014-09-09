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
import com.arjuna.databroker.control.comms.PropertiesDTO;
import com.arjuna.databroker.control.comms.PropertyNamesDTO;
import com.arjuna.databroker.control.core.jee.DataFlowNodeLifeCycleControl;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowFactory;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.InvalidNameException;
import com.arjuna.databroker.data.InvalidPropertyException;
import com.arjuna.databroker.data.MissingPropertyException;

@Path("/dataflowfactory")
@Stateless
public class DataFlowFactoryWS
{
    private static final Logger logger = Logger.getLogger(DataFlowFactoryWS.class.getName());

    @GET
    @Path("_metapropertynames")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyNamesDTO getMetaPropertyNamesJSON()
        throws InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.FINE, "DataFlowFactoryWS.getMetaPropertyNamesJSON");

        if (_dataFlowFactory != null)
            return new PropertyNamesDTO(_dataFlowFactory.getMetaPropertyNames());
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @POST
    @Path("_propertynames")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyNamesDTO getPropertyNamesJSON(PropertiesDTO metaProperties)
        throws InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.FINE, "DataFlowFactoryWS.getPropertyNamesJSON");

        if (_dataFlowFactory != null)
        {
            try
            {
                return new PropertyNamesDTO(_dataFlowFactory.getPropertyNames(metaProperties.getProperties()));
            }
            catch (Throwable throwable)
            {
                throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createDataFlowJSON(@QueryParam("name") String name, CreatePropertiesDTO createProperties)
        throws InvalidNameException, InvalidPropertyException, MissingPropertyException
    {
        logger.log(Level.FINE, "DataFlowFactoryWS.createDataFlowJSON");

        if ((_dataFlowFactory != null) && (_dataFlowInventory != null))
        {
            if (name != null)
            {
                try
                {
                    DataFlow dataFlow = _dataFlowFactory.createDataFlow(name, createProperties.getMetaProperties(), createProperties.getProperties());
                    for (DataFlowNodeFactory dataFlowNodeFactory: _dataFlowNodeFactoryInventory.getDataFlowNodeFactorys())
                        dataFlow.getDataFlowNodeFactoryInventory().addDataFlowNodeFactory(dataFlowNodeFactory);
                    _dataFlowInventory.addDataFlow(dataFlow);

                    return dataFlow.getName();
                }
                catch (Throwable throwable)
                {
                    throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
                }
            }
            else
                throw new WebApplicationException(422); // Unprocessable Entity Error Code
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listDataFlowNamesJSON()
    {
        logger.log(Level.FINE, "DataFlowFactoryWS.listDataFlowNamesJSON");

        if (_dataFlowInventory != null)
        {
            List<String> dataFlowNames = new LinkedList<String>();

            for (DataFlow dataFlow: _dataFlowInventory.getDataFlows())
                dataFlowNames.add(dataFlow.getName());

            return dataFlowNames;
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @DELETE
    @Path("{dataflowid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean removeDataFlowJSON(@PathParam("dataflowid") String dataFlowId)
    {
        logger.log(Level.FINE, "DataFlowFactoryWS.removeDataFlowJSON: " + dataFlowId);

        if (_dataFlowInventory != null)
        {
            if (dataFlowId != null)
            {
                DataFlow dataFlow = _dataFlowInventory.getDataFlow(dataFlowId);
                if (dataFlow != null)
                    for (DataFlowNode dataFlowNode: dataFlow.getDataFlowNodeInventory().getDataFlowNodes())
                        DataFlowNodeLifeCycleControl.removeDataFlowNode(dataFlow, dataFlowNode.getName());

                return _dataFlowInventory.removeDataFlow(dataFlowId);
            }
            else
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @EJB(name="DataFlowFactory")
    private DataFlowFactory _dataFlowFactory;
    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;
    @EJB(name="DataFlowNodeFactoryInventory")
    private DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory;
}