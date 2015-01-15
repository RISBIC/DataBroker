/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.resteasy.util.HttpResponseCodes;
import com.arjuna.databroker.control.comms.CreatePropertiesDTO;
import com.arjuna.databroker.control.comms.DataFlowFactoryDTO;
import com.arjuna.databroker.control.comms.PropertiesDTO;
import com.arjuna.databroker.control.comms.PropertyNamesDTO;

@Stateless
public class DataFlowFactoryClient
{
    private static final Logger logger = Logger.getLogger(DataFlowFactoryClient.class.getName());

    public DataFlowFactorySummary getInfo(String serviceRootURL)
        throws RequestProblemException
    {
        logger.log(Level.FINE, "DataFlowFactoryClient.getMetaPropertyNames: " + serviceRootURL);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/_info");
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<DataFlowFactoryDTO> response = request.get(new GenericType<DataFlowFactoryDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
            {
                DataFlowFactoryDTO dataFlowFactory = response.getEntity();

                return new DataFlowFactorySummary(dataFlowFactory.getName(), dataFlowFactory.getProperties());
            }
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.getMetaPropertyNames: status = " + response.getStatus());

                throw new RequestProblemException("Problem during request of meta-property names");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowFactoryClient.getMetaPropertyNames'", throwable);

            throw new RequestProblemException("Problem requesting of meta-property names");
        }
    }

    public List<String> getMetaPropertyNames(String serviceRootURL)
        throws RequestProblemException
    {
        logger.log(Level.FINE, "DataFlowFactoryClient.getMetaPropertyNames: " + serviceRootURL);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/_metapropertynames");
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<PropertyNamesDTO> response = request.get(new GenericType<PropertyNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getPropertyNames();
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.getMetaPropertyNames: status = " + response.getStatus());

                throw new RequestProblemException("Problem during request of meta-property names");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowFactoryClient.getMetaPropertyNames'", throwable);

            throw new RequestProblemException("Problem requesting of meta-property names");
        }
    }

    public List<String> getPropertyNames(String serviceRootURL, Map<String, String> metaProperties)
        throws RequestProblemException
    {
        logger.log(Level.FINE, "DataFlowFactoryClient.getPropertyNames: " + serviceRootURL + "," + metaProperties);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/_propertynames");
            request.body(MediaType.APPLICATION_JSON, new PropertiesDTO(metaProperties));
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<PropertyNamesDTO> response = request.post(new GenericType<PropertyNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getPropertyNames();
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.getPropertyNames: status = " + response.getStatus());

                throw new RequestProblemException("Problem during request of property names");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowFactoryClient.getPropertyNames'", throwable);

            throw new RequestProblemException("Problem requesting of property names");
        }
    }

    public String createDataFlow(String serviceRootURL, String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws RequestProblemException
    {
        logger.log(Level.FINE, "DataFlowFactoryClient.createDataFlow: " + serviceRootURL + ", " + name + ", " + metaProperties + ", " + properties);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory");
            request.queryParameter("name", name);
            request.body(MediaType.APPLICATION_JSON, new CreatePropertiesDTO(metaProperties, properties));
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<String> response = request.post(new GenericType<String>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.createDataFlow: status = " + response.getStatus());

                throw new RequestProblemException("Problem during request of creation of data flow");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'createDataFlow'", throwable);

            throw new RequestProblemException("Problem requesting of creation of data flow");
        }
    }

    public boolean removeDataFlow(String serviceRootURL, String dataFlowId)
        throws RequestProblemException
    {
        logger.log(Level.FINE, "DataFlowFactoryClient.removeDataFlow: " + serviceRootURL + ", " + dataFlowId);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/{dataflowid}");
            request.pathParameter("dataflowid", dataFlowId);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<Boolean> response = request.delete(new GenericType<Boolean>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "Problem with rest call for 'removeDataFlow'");

                throw new RequestProblemException("Problem during request of removal of data flow");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'removeDataFlow'", throwable);

            throw new RequestProblemException("Problem requesting of removal of data flow");
        }
    }
}
