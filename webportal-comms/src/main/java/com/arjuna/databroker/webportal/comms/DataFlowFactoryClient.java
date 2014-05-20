/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.Collections;
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
import com.arjuna.databroker.control.comms.PropertiesDTO;
import com.arjuna.databroker.control.comms.PropertyNamesDTO;

@Stateless
public class DataFlowFactoryClient
{
    private static final Logger logger = Logger.getLogger(DataFlowFactoryClient.class.getName());

    public List<String> getMetaPropertyNames(String serviceRootURL)
    {
        logger.fine("DataFlowFactoryClient.getMetaPropertyNames: " + serviceRootURL);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/_metapropertynames");
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<PropertyNamesDTO> response = request.get(new GenericType<PropertyNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getPropertyNames();
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.getMetaPropertyNames: status = " + response.getStatus());

                return Collections.emptyList();
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowFactoryClient.getMetaPropertyNames'", throwable);

            return Collections.emptyList();
        }
    }

    public List<String> getPropertyNames(String serviceRootURL, Map<String, String> metaProperties)
    {
        logger.fine("DataFlowFactoryClient.getPropertyNames: " + serviceRootURL + "," + metaProperties);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/_propertynames");
            request.body(MediaType.APPLICATION_JSON, new PropertiesDTO(metaProperties));
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<PropertyNamesDTO> response = request.post(new GenericType<PropertyNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getPropertyNames();
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.getPropertyNames: status = " + response.getStatus());

                return Collections.emptyList();
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowFactoryClient.getPropertyNames'", throwable);

            return Collections.emptyList();
        }
    }

    public String createDataFlow(String serviceRootURL, String name, Map<String, String> metaProperties, Map<String, String> properties)
    {
        logger.fine("DataFlowFactoryClient.createDataFlow: " + serviceRootURL + ", " + name + ", " + metaProperties + ", " + properties);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory");
            request.queryParameter("name", name);
            request.body(MediaType.APPLICATION_JSON, new CreatePropertiesDTO(metaProperties, properties));
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<String> response = request.post(new GenericType<String>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else
            {
                logger.log(Level.WARNING, "DataFlowFactoryClient.createDataFlow: status = " + response.getStatus());

                return null;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'createDataFlow'", throwable);

            return null;
        }
    }

    public boolean removeDataFlow(String serviceRootURL, String dataFlowId)
    {
        logger.fine("DataFlowFactoryClient.removeDataFlow: " + serviceRootURL + ", " + dataFlowId);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflowfactory/{dataflowid}");
            request.pathParameter("dataflowid", dataFlowId);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<Boolean> response = request.delete(new GenericType<Boolean>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else
            {
                logger.log(Level.WARNING, "Problem with rest call for 'removeDataFlow'");

                return false;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'removeDataFlow'", throwable);

            return false;
        }
    }
}
