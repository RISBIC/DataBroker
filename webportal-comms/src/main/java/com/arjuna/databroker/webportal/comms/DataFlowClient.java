/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.Collections;
import java.util.HashMap;
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
import com.arjuna.databroker.control.comms.ClassNamesDTO;
import com.arjuna.databroker.control.comms.CreatePropertiesDTO;
import com.arjuna.databroker.control.comms.DataFlowDTO;
import com.arjuna.databroker.control.comms.DataFlowNodeDTO;
import com.arjuna.databroker.control.comms.DataFlowNodeFactoryDTO;
import com.arjuna.databroker.control.comms.FactoryNamesDTO;
import com.arjuna.databroker.control.comms.PropertiesDTO;
import com.arjuna.databroker.control.comms.PropertyNamesDTO;

@Stateless
public class DataFlowClient
{
    private static final Logger logger = Logger.getLogger(DataFlowClient.class.getName());

    public List<String> getDataFlowNodeClassNames(String serviceRootURL, String dataflowId, String factoryName)
    {
        logger.fine("DataFlowClient.getDataFlowNodeClassNames: " + serviceRootURL + ", " + dataflowId + ", " + factoryName);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflow/{dataflowid}/_dataflownodeclassnames");
            request.pathParameter("dataflowid", dataflowId);
            request.queryParameter("factoryname", factoryName);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<ClassNamesDTO> response = request.get(new GenericType<ClassNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getClassNames();
            else
            {
                logger.log(Level.WARNING, "DataFlowClient.getDataFlowNodeClassNames: status = " + response.getStatus());

                return Collections.emptyList();
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowClient.getDataFlowNodeClassNames'", throwable);

            return Collections.emptyList();
        }
    }

    public List<String> getFactoryNames(String serviceRootURL, String dataFlowId, String dataFlowNodeClassName)
    {
        logger.fine("DataFlowClient.getFactoryNames: " + serviceRootURL + ", " + dataFlowId);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflow/{dataflowid}/_factorynames");
            request.pathParameter("dataflowid", dataFlowId);
            request.queryParameter("dataflownodeclassname", dataFlowNodeClassName);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<FactoryNamesDTO> response = request.get(new GenericType<FactoryNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getFactoryNames();
            else
            {
                logger.log(Level.WARNING, "DataFlowClient.getFactoryNames: status = " + response.getStatus());

                return Collections.emptyList();
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowClient.getFactoryNames'", throwable);

            return Collections.emptyList();
        }
    }

    public List<String> getMetaPropertyNames(String serviceRootURL, String dataFlowId, String dataFlowNodeClassName, String factoryName)
    {
        logger.info("DataFlowClient.getMetaPropertyNames: " + serviceRootURL + ", " + dataFlowId + ", " + dataFlowNodeClassName + ", " + factoryName);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflow/{dataflowid}/_metapropertynames");
            request.pathParameter("dataflowid", dataFlowId);
            request.queryParameter("dataflownodeclassname", dataFlowNodeClassName);
            request.queryParameter("factoryname", factoryName);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<PropertyNamesDTO> response = request.get(new GenericType<PropertyNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getPropertyNames();
            else
            {
                logger.log(Level.WARNING, "DataFlowClient.getMetaPropertyNames: status = " + response.getStatus());

                return Collections.emptyList();
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowClient.getMetaPropertyNames'", throwable);

            return Collections.emptyList();
        }
    }

    public List<String> getPropertyNames(String serviceRootURL, String dataFlowId, String dataFlowNodeClassName, String factoryName, Map<String, String> metaProperties)
    {
        logger.fine("DataFlowClient.getPropertyNames: " + serviceRootURL + ", " + dataFlowId + "," + dataFlowNodeClassName + "," + factoryName + "," + metaProperties);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflow/{dataflowId}/_propertynames");
            request.pathParameter("dataflowId", dataFlowId);
            request.queryParameter("dataflownodeclassname", dataFlowNodeClassName);
            request.queryParameter("factoryname", factoryName);
            request.body(MediaType.APPLICATION_JSON, new PropertiesDTO(metaProperties));

            ClientResponse<PropertyNamesDTO> response = request.post(new GenericType<PropertyNamesDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity().getPropertyNames();
            else
            {
                logger.log(Level.WARNING, "DataFlowClient.getPropertyNames: status = " + response.getStatus());

                return Collections.emptyList();
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowClient.getPropertyNames'", throwable);

            return Collections.emptyList();
        }
    }

    public String createDataFlowNode(String serviceRootURL, String dataFlowId, String dataFlowNodeClassName, String factoryName, Map<String, String> metaProperties, String name, Map<String, String> properties)
    {
        logger.fine("DataFlowClient.createDataFlowNode: " + serviceRootURL + ", " + dataFlowId + ", " + dataFlowNodeClassName +  ", "  + factoryName + ", " + metaProperties + ", " + name + ", " + properties);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflow/{dataflowId}");
            request.pathParameter("dataflowId", dataFlowId);
            request.queryParameter("factoryname", factoryName);
            request.queryParameter("name", name);
            request.queryParameter("dataflownodeclassname", dataFlowNodeClassName);
            request.body(MediaType.APPLICATION_JSON, new CreatePropertiesDTO(metaProperties, properties));

            ClientResponse<String> response = request.post(new GenericType<String>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
                return response.getEntity();
            else
            {
                logger.log(Level.WARNING, "DataFlowClient.createDataFlowNode: status = " + response.getStatus());

                return null;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowClient.createDataFlowNode'", throwable);

            return null;
        }
    }

    public String getDataFlow(String serviceRootURL, String dataFlowId, Map<String, String> attributes, Map<String, String> properties, Map<String, Map<String, String>> dataFlowNodeAttributesMap, Map<String, Map<String, String>> dataFlowNodePropertiesMap, List<DataFlowNodeFactorySummary> dataFlowNodeFactories)
    {
        logger.fine("DataFlowClient.getDataFlow: " + serviceRootURL + ", " + dataFlowId + ", " + attributes + ", " + properties + ", " + dataFlowNodeAttributesMap + ", " + dataFlowNodePropertiesMap + ", " + dataFlowNodeFactories);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/dataflow/{dataflowId}");
            request.pathParameter("dataflowId", dataFlowId);
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<DataFlowDTO> response = request.get(new GenericType<DataFlowDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
            {
                DataFlowDTO dataFlow = response.getEntity();

                attributes.clear();
                attributes.put("Name", dataFlow.getName());

                properties.clear();
                properties.putAll(dataFlow.getProperties().getProperties());

                dataFlowNodeAttributesMap.clear();
                dataFlowNodePropertiesMap.clear();
                for (DataFlowNodeDTO dataflowNode: dataFlow.getDataFlowNodes())
                {
                    Map<String, String> dataFlowNodeAttributes = new HashMap<String, String>();
                    dataFlowNodeAttributes.put("Name", dataflowNode.getName());
                    dataFlowNodeAttributes.put("Type", dataflowNode.getType());
                    dataFlowNodeAttributesMap.put(dataflowNode.getName(), dataFlowNodeAttributes);

                    Map<String, String> dataFlowNodeProperties = new HashMap<String, String>();
                    dataFlowNodeProperties.putAll(dataflowNode.getProperties());
                    dataFlowNodePropertiesMap.put(dataflowNode.getName(), dataFlowNodeProperties);
                }

                dataFlowNodeFactories.clear();
                for (DataFlowNodeFactoryDTO dataFlowNodeFactory: dataFlow.getDataFlowNodeFactories())
                    dataFlowNodeFactories.add(new DataFlowNodeFactorySummary(dataFlowNodeFactory.getName(), dataFlowNodeFactory.isDataSourceFactory(), dataFlowNodeFactory.isDataSinkFactory(), dataFlowNodeFactory.isDataProcessorFactory(), dataFlowNodeFactory.isDataServiceFactory(), dataFlowNodeFactory.isDataStoreFactory()));

                logger.fine("DataFlowClient.getDataFlow: " + serviceRootURL + ", " + dataFlowId + ", " + attributes + ", " + properties + ", " + dataFlowNodeAttributesMap + ", " + dataFlowNodePropertiesMap);

                return dataFlow.getId();
            }
            else
            {
                logger.log(Level.WARNING, "DataFlowClient.getDataFlow: status = " + response.getStatus());

                return null;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataFlowClient.getDataFlow'", throwable);

            return null;
        }
    }
}
