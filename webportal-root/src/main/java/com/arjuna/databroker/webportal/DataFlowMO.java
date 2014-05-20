/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.DataFlowClient;
import com.arjuna.databroker.webportal.comms.DataFlowNodeFactorySummary;

@SessionScoped
@ManagedBean(name="dataflow")
public class DataFlowMO implements Serializable
{
    private static final Logger logger = Logger.getLogger(DataFlowMO.class.getName());

    private static final long serialVersionUID = 9217572774218964749L;

    public DataFlowMO()
    {
        _serviceRootURL = null;
        _id             = null;

        _attributes                 = null;
        _properties                 = null;
        _dataFlowNodesJSON          = null;
        _dataFlowNodeId             = null;
        _dataFlowNodeMetaProperties = null;
        _dataFlowNodeAttributes     = null;
        _dataFlowNodeProperties     = null;
        _dataFlowNodeFactories      = null;

        _errorMessage = null;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public String getId()
    {
        return _id;
    }

    public List<PropertyVO> getAttributes()
    {
        return _attributes;
    }

    public List<PropertyVO> getProperties()
    {
        return _properties;
    }

    public String getDataFlowNodeId()
    {
        return _dataFlowNodeId;
    }

    public String getDataFlowNodesJSON()
    {
        return _dataFlowNodesJSON;
    }

    public void setDataFlowNodesJSON(String dataFlowNodesJSON)
    {
        _dataFlowNodesJSON = dataFlowNodesJSON;
    }

    public void setDataFlowNodeId(String dataFlowNodeId)
    {
        logger.log(Level.FINE, "DataFlowMO.setDataFlowNodeId: " + dataFlowNodeId);
       
        _dataFlowNodeId = dataFlowNodeId;
    }

    public List<PropertyVO> getDataFlowNodeMetaProperties()
    {
        return _dataFlowNodeMetaProperties;
    }

    public List<PropertyVO> getDataFlowNodeAttributes()
    {
        return _dataFlowNodeAttributes;
    }

    public List<PropertyVO> getDataFlowNodeProperties()
    {
        return _dataFlowNodeProperties;
    }

    public List<DataFlowNodeFactorySummaryVO> getDataFlowNodeFactories()
    {
        return _dataFlowNodeFactories;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public String doLoad(String serviceRootURL, String dataFlowNodeId)
    {
        logger.log(Level.FINE, "DataFlowMO.doLoad: " + serviceRootURL + ", " + dataFlowNodeId);

        _serviceRootURL = serviceRootURL;
        _id             = dataFlowNodeId;

        load();

        return "dataflow?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "DataFlowMO.doReload");

        load();

        return "dataflow?faces-redirect=true";
    }

    public void load()
    {
        logger.log(Level.FINE, "DataFlowMO.load");

        _dataFlowNodeId = null;

        _errorMessage = null;
        if ((_serviceRootURL != null) && (_id != null))
        {
            Map<String, String>              attributesMap             = new HashMap<String, String>();
            Map<String, String>              propertiesMap             = new HashMap<String, String>();
            Map<String, Map<String, String>> dataFlowNodeAttributesMap = new HashMap<String, Map<String, String>>();
            Map<String, Map<String, String>> dataFlowNodePropertiesMap = new HashMap<String, Map<String, String>>();
            List<DataFlowNodeFactorySummary> dataFlowNodeFactories     = new LinkedList<DataFlowNodeFactorySummary>();

            String id = _dataFlowClient.getDataFlow(_serviceRootURL, _id, attributesMap, propertiesMap, dataFlowNodeAttributesMap, dataFlowNodePropertiesMap, dataFlowNodeFactories);

            if (id != null)
            {
                _attributes = PropertyVO.fromMap(attributesMap);
                _properties = PropertyVO.fromMap(propertiesMap);

                _dataFlowNodesJSON = dataFlowNodesToJSON(dataFlowNodeAttributesMap, dataFlowNodePropertiesMap);
                logger.log(Level.FINER, "DataFlowMO.load - dataFlowNodesJSON = " + _dataFlowNodesJSON);

                _dataFlowNodeFactories = new LinkedList<DataFlowNodeFactorySummaryVO>();
                for (DataFlowNodeFactorySummary dataFlowNodeFactory: dataFlowNodeFactories)
                    _dataFlowNodeFactories.add(new DataFlowNodeFactorySummaryVO(dataFlowNodeFactory.getName(), dataFlowNodeFactory.isDataSourceFactory(), dataFlowNodeFactory.isDataSinkFactory(), dataFlowNodeFactory.isDataProcessorFactory(), dataFlowNodeFactory.isDataServiceFactory(), dataFlowNodeFactory.isDataStoreFactory()));
            }
            else
            {
                _attributes                 = null;
                _properties                 = null;
                _dataFlowNodesJSON          = null;
                _dataFlowNodeId             = null;
                _dataFlowNodeMetaProperties = null;
                _dataFlowNodeAttributes     = null;
                _dataFlowNodeProperties     = null;
                _dataFlowNodeFactories      = null;

                _errorMessage = "Unsuccessful query of DataBroker!";
            }
        }
        else
            _errorMessage = "Unable to query DataBroker";
    }

    private String dataFlowNodesToJSON(Map<String, Map<String, String>> dataFlowNodeAttributesMap, Map<String, Map<String, String>> dataFlowNodePropertiesMap)
    {
        StringBuffer dataFlowNodesJSONBuffer = new StringBuffer();

        dataFlowNodesJSONBuffer.append("'{ \"dataFlowNodes\": [ ");
        boolean firstDataFlowNode = true;
        for (String dataFlowNodeName: dataFlowNodeAttributesMap.keySet())
        {
            if (firstDataFlowNode)
                firstDataFlowNode = false;
            else
                dataFlowNodesJSONBuffer.append(", ");

            dataFlowNodesJSONBuffer.append("{ \"attributes\": { ");
            boolean firstDataFlowNodeAttribute = true;
            for (Entry<String, String> dataFlowNodeAttribute: dataFlowNodeAttributesMap.get(dataFlowNodeName).entrySet())
            {
                if (firstDataFlowNodeAttribute)
                    firstDataFlowNodeAttribute = false;
                else
                    dataFlowNodesJSONBuffer.append(", ");

                dataFlowNodesJSONBuffer.append("\"" + dataFlowNodeAttribute.getKey() + "\": \"" + dataFlowNodeAttribute.getValue() + "\"");
            }
            dataFlowNodesJSONBuffer.append(" }, ");

            dataFlowNodesJSONBuffer.append("\"properties\": { ");
            if (dataFlowNodePropertiesMap.get(dataFlowNodeName) != null)
            {
                boolean firstDataFlowNodeProperty = true;
                for (Entry<String, String> dataFlowNodeProperty: dataFlowNodePropertiesMap.get(dataFlowNodeName).entrySet())
                {
                    if (firstDataFlowNodeProperty)
                        firstDataFlowNodeProperty = false;
                    else
                        dataFlowNodesJSONBuffer.append(", ");

                    dataFlowNodesJSONBuffer.append("\"" + dataFlowNodeProperty.getKey() + "\": \"" + dataFlowNodeProperty.getValue() + "\"");
                }
            }
            dataFlowNodesJSONBuffer.append(" }");

            dataFlowNodesJSONBuffer.append(" }");
        }
        dataFlowNodesJSONBuffer.append(" ] }'");

        return dataFlowNodesJSONBuffer.toString();
    }

    private String _serviceRootURL;
    private String _id;

    private List<PropertyVO>                   _attributes;
    private List<PropertyVO>                   _properties;
    private String                             _dataFlowNodesJSON;
    private String                             _dataFlowNodeId;
    private List<PropertyVO>                   _dataFlowNodeMetaProperties;
    private List<PropertyVO>                   _dataFlowNodeAttributes;
    private List<PropertyVO>                   _dataFlowNodeProperties;
    private List<DataFlowNodeFactorySummaryVO> _dataFlowNodeFactories;

    private String _errorMessage;

    @EJB
    private DataFlowClient _dataFlowClient;
}
