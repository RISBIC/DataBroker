/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.DataBrokerClient;
import com.arjuna.databroker.webportal.comms.DataBrokerSummary;
import com.arjuna.databroker.webportal.comms.DataFlowClient;
import com.arjuna.databroker.webportal.comms.DataFlowNodeLinkSummary;
import com.arjuna.databroker.webportal.comms.DataFlowNodeFactorySummary;
import com.arjuna.databroker.webportal.comms.DataFlowNodeLinkClient;
import com.arjuna.databroker.webportal.comms.DataFlowNodeSummary;
import com.arjuna.databroker.webportal.comms.RequestProblemException;

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

        _attributes                  = null;
        _properties                  = null;
        _dataFlowNodesJSON           = null;
        _dataFlowNodeId              = null;
        _dataFlowNodeMetaProperties  = null;
        _dataFlowNodeAttributes      = null;
        _dataFlowNodeProperties      = null;
        _dataFlowNodeFactories       = null;
        _selectedDataFlowNodeFactory = null;
        _dataFlowNode                = "";
        _sourceDataFlowNode          = "";
        _processorDataFlowNode       = "";
        _sinkDataFlowNode            = "";
        _serviceDataFlowNode         = "";
        _storeDataFlowNode           = "";
        _linkSourceDataFlowNode      = "";
        _linkSinkDataFlowNode        = "";
        _linkedDataFlowNodes         = false;

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

    public List<DataFlowNodeFactorySummaryVO> getAvailableDataFlowNodeFactories()
    {
        return _availableDataFlowNodeFactories;
    }

    public DataFlowNodeFactorySummaryVO getSelectedDataFlowNodeFactory()
    {
        return _selectedDataFlowNodeFactory;
    }

    public String getDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getDataFlowNode: " + _dataFlowNode);

        return _dataFlowNode;
    }

    public void setDataFlowNode(String dataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setDataFlowNode: " + dataFlowNode);

        _dataFlowNode = dataFlowNode;
    }

    public String getSourceDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getSourceDataFlowNode: " + _sourceDataFlowNode);

        return _sourceDataFlowNode;
    }

    public void setSourceDataFlowNode(String sourceDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setSourceDataFlowNode: " + sourceDataFlowNode);

        _sourceDataFlowNode = sourceDataFlowNode;
    }

    public String getProcessorDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getProcessorDataFlowNode: " + _processorDataFlowNode);

        return _processorDataFlowNode;
    }

    public void setProcessorDataFlowNode(String processorDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setProcessorDataFlowNode: " + processorDataFlowNode);

        _processorDataFlowNode = processorDataFlowNode;
    }

    public String getSinkDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getSinkDataFlowNode: " + _sinkDataFlowNode);

        return _sinkDataFlowNode;
    }

    public void setSinkDataFlowNode(String sinkDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setSinkDataFlowNode: " + sinkDataFlowNode);

        _sinkDataFlowNode = sinkDataFlowNode;
    }

    public String getServiceDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getServiceDataFlowNode: " + _serviceDataFlowNode);

        return _serviceDataFlowNode;
    }

    public void setServiceDataFlowNode(String serviceDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setServiceDataFlowNode: " + serviceDataFlowNode);

        _serviceDataFlowNode = serviceDataFlowNode;
    }

    public String getStoreDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getStoreDataFlowNode: " + _storeDataFlowNode);

        return _storeDataFlowNode;
    }

    public void setStoreDataFlowNode(String storeDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setStoreDataFlowNode: " + storeDataFlowNode);

        _storeDataFlowNode = storeDataFlowNode;
    }

    public String getLinkSourceDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getLinkSourceDataFlowNode: " + _linkSourceDataFlowNode);

        return _linkSourceDataFlowNode;
    }

    public void setLinkSourceDataFlowNode(String linkSourceDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setLinkSourceDataFlowNode: " + linkSourceDataFlowNode);

        _linkSourceDataFlowNode = linkSourceDataFlowNode;
    }

    public String getLinkSinkDataFlowNode()
    {
        logger.log(Level.FINER, "DataFlowMO.getLinkSinkDataFlowNode: " + _linkSinkDataFlowNode);

        return _linkSinkDataFlowNode;
    }

    public void setLinkSinkDataFlowNode(String linkSinkDataFlowNode)
    {
        logger.log(Level.FINER, "DataFlowMO.setLinkSinkDataFlowNode: " + linkSinkDataFlowNode);

        _linkSinkDataFlowNode = linkSinkDataFlowNode;
    }

    public Boolean getLinkedDataFlowNodes()
    {
        logger.log(Level.FINER, "DataFlowMO.getLinkedDataFlowNodes: " + _linkedDataFlowNodes);

        return _linkedDataFlowNodes;
    }

    public void setLinkedDataFlowNodes(Boolean linkedDataFlowNodes)
    {
        logger.log(Level.FINER, "DataFlowMO.setLinkedDataFlowNodes: " + _linkedDataFlowNodes);

        _linkedDataFlowNodes = linkedDataFlowNodes;
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

        return "/dataflows/dataflow_attributes?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "DataFlowMO.doReload");

        load();

        return "#";
    }

    public String doReloadToNodesTab()
    {
        logger.log(Level.FINE, "DataFlowMO.doReloadToNodesTab");

        load();

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doToAttributesTab()
    {
        return "/dataflows/dataflow_attributes?faces-redirect=true";
    }

    public String doToNodesTab()
    {
        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doToNodeFactoriesTab()
    {
        return "/dataflows/dataflow_nodefactories?faces-redirect=true";
    }

    public String doExamineDataFlowNode()
    {
        logger.log(Level.FINE, "DataFlowMO.doExamineDataFlowNode: [" + _dataFlowNode + "]");

        if ((_dataFlowNode != null) && (! "".equals(_dataFlowNode)))
        {
            _errorMessage = null;
            try
            {
                DataFlowNodeSummary dataFlowNodeSummary = _dataFlowClient.getDataFlowNodeInfo(_serviceRootURL, _id, _dataFlowNode);

                _dataFlowNodeAttributes = new LinkedList<PropertyVO>();
                _dataFlowNodeAttributes.add(new PropertyVO("Name", dataFlowNodeSummary.getName()));
                _dataFlowNodeAttributes.add(new PropertyVO("Type", dataFlowNodeSummary.getType()));
                _dataFlowNodeProperties = PropertyVO.fromMap(dataFlowNodeSummary.getProperties());
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Processor node not specified";

        if (_errorMessage == null)
            return "/dataflows/dataflow_node_attributes?faces-redirect=true";
        else
            return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doRemoveSourceDataFlowNode()
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveSourceDataFlowNode: [" + _sourceDataFlowNode + "]");

        if ((_sourceDataFlowNode != null) && (! "".equals(_sourceDataFlowNode)))
        {
            _errorMessage = null;
            try
            {
                _dataFlowClient.removeDataFlowNode(_serviceRootURL, _id, _sourceDataFlowNode);

                load();
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Source node not specified";

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doRemoveProcessorDataFlowNode()
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveProcessorDataFlowNode: [" + _processorDataFlowNode + "]");

        if ((_processorDataFlowNode != null) && (! "".equals(_processorDataFlowNode)))
        {
            _errorMessage = null;
            try
            {
                _dataFlowClient.removeDataFlowNode(_serviceRootURL, _id, _processorDataFlowNode);

                load();
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Processor node not specified";

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doRemoveSinkDataFlowNode()
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveSinkDataFlowNode: [" + _sinkDataFlowNode + "]");

        if ((_sinkDataFlowNode != null) && (! "".equals(_sinkDataFlowNode)))
        {
            _errorMessage = null;
            try
            {
                _dataFlowClient.removeDataFlowNode(_serviceRootURL, _id, _sinkDataFlowNode);

                load();
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Sink node not specified";

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doRemoveServiceDataFlowNode()
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveServiceDataFlowNode: [" + _serviceDataFlowNode + "]");

        if ((_serviceDataFlowNode != null) && (! "".equals(_serviceDataFlowNode)))
        {
            _errorMessage = null;
            try
            {
                _dataFlowClient.removeDataFlowNode(_serviceRootURL, _id, _serviceDataFlowNode);

                load();
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Service node not specified";

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doRemoveStoreDataFlowNode()
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveStoreDataFlowNode: [" + _storeDataFlowNode + "]");

        if ((_storeDataFlowNode != null) && (! "".equals(_storeDataFlowNode)))
        {
            _errorMessage = null;
            try
            {
                _dataFlowClient.removeDataFlowNode(_serviceRootURL, _id, _storeDataFlowNode);

                load();
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Store node not specified";

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doCreateLink()
    {
        logger.log(Level.FINE, "DataFlowMO.doCreateLink: [" + _linkSourceDataFlowNode + "] [" + _linkSinkDataFlowNode + "]");

        try
        {
            _errorMessage = null;
            if ((_linkSourceDataFlowNode != null) && (! "".equals(_linkSourceDataFlowNode)) && (_linkSinkDataFlowNode != null) && (! "".equals(_linkSinkDataFlowNode)))
                if (_dataFlowNodeLinkClient.createDataFlowNodeLink(_serviceRootURL, _id, _linkSourceDataFlowNode, _linkSinkDataFlowNode))
                    load();
        }
        catch (RequestProblemException requestProblemException)
        {
            _errorMessage = requestProblemException.getMessage();
        }

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doRemoveLink()
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveLink: [" + _linkSourceDataFlowNode + "] [" + _linkSinkDataFlowNode + "]");

        try
        {
            _errorMessage = null;
            if ((_linkSourceDataFlowNode != null) && (! "".equals(_linkSourceDataFlowNode)) && (_linkSinkDataFlowNode != null) && (! "".equals(_linkSinkDataFlowNode)))
                if (_dataFlowNodeLinkClient.removeDataFlowNodeLink(_serviceRootURL, _id, _linkSourceDataFlowNode, _linkSinkDataFlowNode))
                    load();
        }
        catch (RequestProblemException requestProblemException)
        {
            _errorMessage = requestProblemException.getMessage();
        }

        return "/dataflows/dataflow_nodes?faces-redirect=true";
    }

    public String doExamineDataFlowNodeFactory(String dataFlowNodeFactoryName)
    {
        logger.log(Level.FINE, "DataFlowMO.doExamineDataFlowNodeFactory: [" + dataFlowNodeFactoryName + "]");

        try
        {
            _errorMessage = null;
            for (DataFlowNodeFactorySummaryVO dataFlowNodeFactorySummary: _dataFlowNodeFactories)
            	for (PropertyVO property: dataFlowNodeFactorySummary.getAttributes())
                    if (property.getName().equals("Name") && property.getValue().equals(dataFlowNodeFactoryName))
                        _selectedDataFlowNodeFactory = dataFlowNodeFactorySummary;

            if (_selectedDataFlowNodeFactory == null)
                for (DataFlowNodeFactorySummaryVO dataFlowNodeFactorySummary: _availableDataFlowNodeFactories)
                    for (PropertyVO property: dataFlowNodeFactorySummary.getAttributes())
                        if (property.getName().equals("Name") && property.getValue().equals(dataFlowNodeFactoryName))
                            _selectedDataFlowNodeFactory = dataFlowNodeFactorySummary;
        }
        catch (Throwable throwable)
        {
            _errorMessage = "Problem obtaining information about selected data flow node factory";
        }

        return "/dataflows/dataflow_nodefactory_attributes?faces-redirect=true";
    }

    public String doAddDataFlowNodeFactory(String dataFlowNodeFactoryName)
    {
        logger.log(Level.FINE, "DataFlowMO.doAddDataFlowNodeFactory: [" + dataFlowNodeFactoryName + "]");

        try
        {
            _errorMessage = null;
            if (_dataFlowClient.addDataFlowNodeFactory(_serviceRootURL, _id, dataFlowNodeFactoryName))
                load();
            else
                _errorMessage = "Problem adding selected data flow node factory to data flow";
        }
        catch (Throwable throwable)
        {
            _errorMessage = "Problem adding selected data flow node factory";
        }

        return "/dataflows/dataflow_nodefactories?faces-redirect=true";
    }

    public String doRemoveDataFlowNodeFactory(String dataFlowNodeFactoryName)
    {
        logger.log(Level.FINE, "DataFlowMO.doRemoveDataFlowNodeFactory: [" + dataFlowNodeFactoryName + "]");

        try
        {
            _errorMessage = null;
            if (_dataFlowClient.removeDataFlowNodeFactory(_serviceRootURL, _id, dataFlowNodeFactoryName))
                load();
            else
                _errorMessage = "Problem adding selected data flow node factory to data flow";
        }
        catch (Throwable throwable)
        {
            _errorMessage = "Problem removing selected data flow node factory";
        }

        return "/dataflows/dataflow_nodefactories?faces-redirect=true";
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
            List<DataFlowNodeLinkSummary>    dataFlowNodeLinks         = new LinkedList<DataFlowNodeLinkSummary>();
            List<DataFlowNodeFactorySummary> dataFlowNodeFactories     = new LinkedList<DataFlowNodeFactorySummary>();

            DataBrokerSummary dataBrokerSummary = null;
            try
            {
                dataBrokerSummary = _dataBrokerClient.getDataBrokerSummaries(_serviceRootURL);
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }

            String id = null;
            try
            {
                id = _dataFlowClient.getDataFlow(_serviceRootURL, _id, attributesMap, propertiesMap, dataFlowNodeAttributesMap, dataFlowNodePropertiesMap, dataFlowNodeLinks, dataFlowNodeFactories);
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }

            if ((id != null) && (dataBrokerSummary != null))
            {
                _availableDataFlowNodeFactories = new LinkedList<DataFlowNodeFactorySummaryVO>();
                for (DataFlowNodeFactorySummary dataFlowNodeFactorySummary: dataBrokerSummary.getDataFlowNodeFactorySummaries())
                    _availableDataFlowNodeFactories.add(new DataFlowNodeFactorySummaryVO(dataFlowNodeFactorySummary.getAttributes(), dataFlowNodeFactorySummary.getProperties(), dataFlowNodeFactorySummary.isDataSourceFactory(), dataFlowNodeFactorySummary.isDataSinkFactory(), dataFlowNodeFactorySummary.isDataProcessorFactory(), dataFlowNodeFactorySummary.isDataServiceFactory(), dataFlowNodeFactorySummary.isDataStoreFactory()));

                _attributes = PropertyVO.fromMap(attributesMap);
                _properties = PropertyVO.fromMap(propertiesMap);

                _dataFlowNodesJSON = dataFlowNodesToJSON(dataFlowNodeAttributesMap, dataFlowNodePropertiesMap, dataFlowNodeLinks);
                logger.log(Level.FINER, "DataFlowMO.load - dataFlowNodesJSON = " + _dataFlowNodesJSON);

                _dataFlowNodeFactories          = new LinkedList<DataFlowNodeFactorySummaryVO>();
                for (DataFlowNodeFactorySummary dataFlowNodeFactory: dataFlowNodeFactories)
                {
                    _dataFlowNodeFactories.add(new DataFlowNodeFactorySummaryVO(dataFlowNodeFactory.getAttributes(), dataFlowNodeFactory.getProperties(), dataFlowNodeFactory.isDataSourceFactory(), dataFlowNodeFactory.isDataSinkFactory(), dataFlowNodeFactory.isDataProcessorFactory(), dataFlowNodeFactory.isDataServiceFactory(), dataFlowNodeFactory.isDataStoreFactory()));

                    ListIterator<DataFlowNodeFactorySummaryVO> availableDataFlowNodeFactoryIterator = _availableDataFlowNodeFactories.listIterator();
                    while (availableDataFlowNodeFactoryIterator.hasNext())
                    {
                        DataFlowNodeFactorySummaryVO availableDataFlowNodeFactory = availableDataFlowNodeFactoryIterator.next();
                        if (availableDataFlowNodeFactory.getName().equals(dataFlowNodeFactory.getAttributes().get("Name")))
                            availableDataFlowNodeFactoryIterator.remove();
                    }
                }
            }
            else
            {
                _attributes                     = null;
                _properties                     = null;
                _dataFlowNodesJSON              = null;
                _dataFlowNodeId                 = null;
                _dataFlowNodeMetaProperties     = null;
                _dataFlowNodeAttributes         = null;
                _dataFlowNodeProperties         = null;
                _dataFlowNodeFactories          = null;
                _availableDataFlowNodeFactories = null;

                if (_errorMessage == null)
                    _errorMessage = "Unsuccessful query of DataBroker!";
            }

            _dataFlowNode           = "";
            _sourceDataFlowNode     = "";
            _processorDataFlowNode  = "";
            _sinkDataFlowNode       = "";
            _serviceDataFlowNode    = "";
            _storeDataFlowNode      = "";
            _linkSourceDataFlowNode = "";
            _linkSinkDataFlowNode   = "";
            _linkedDataFlowNodes    = false;
        }
        else
            _errorMessage = "Unable to query DataBroker";
    }

    private String dataFlowNodesToJSON(Map<String, Map<String, String>> dataFlowNodeAttributesMap, Map<String, Map<String, String>> dataFlowNodePropertiesMap, List<DataFlowNodeLinkSummary> dataFlowNodeLinks)
    {
        StringBuffer dataFlowNodesJSONBuffer = new StringBuffer();

        dataFlowNodesJSONBuffer.append("'{ ");
        dataFlowNodesJSONBuffer.append("\"dataFlowNodes\": [ ");
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
        dataFlowNodesJSONBuffer.append(" ], ");

        dataFlowNodesJSONBuffer.append("\"dataFlowNodeLinks\": [ ");
        boolean firstDataFlowNodeLink = true;
        for (DataFlowNodeLinkSummary dataFlowNodeLink: dataFlowNodeLinks)
        {
            if (firstDataFlowNodeLink)
                firstDataFlowNodeLink = false;
            else
                dataFlowNodesJSONBuffer.append(", ");

            dataFlowNodesJSONBuffer.append(" { ");
            dataFlowNodesJSONBuffer.append("\"sourceDataFlowNodeName\": \"" + dataFlowNodeLink.getSourceDataFlowNodeName() + "\", ");
            dataFlowNodesJSONBuffer.append("\"sinkDataFlowNodeName\": \"" + dataFlowNodeLink.getSinkDataFlowNodeName() + "\"");
            dataFlowNodesJSONBuffer.append(" }");
        }
        dataFlowNodesJSONBuffer.append(" ]");

        dataFlowNodesJSONBuffer.append(" }'");

        if (logger.isLoggable(Level.FINER))
            logger.log(Level.FINER, "DataFlowMO.dataFlowNodesToJSON: <" + dataFlowNodesJSONBuffer.toString() + ">");

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
    private List<DataFlowNodeFactorySummaryVO> _availableDataFlowNodeFactories;
    private DataFlowNodeFactorySummaryVO       _selectedDataFlowNodeFactory;
    private String                             _dataFlowNode;
    private String                             _sourceDataFlowNode;
    private String                             _processorDataFlowNode;
    private String                             _sinkDataFlowNode;
    private String                             _serviceDataFlowNode;
    private String                             _storeDataFlowNode;
    private String                             _linkSourceDataFlowNode;
    private String                             _linkSinkDataFlowNode;
    private Boolean                            _linkedDataFlowNodes;

    private String _errorMessage;

    @EJB
    private DataFlowClient _dataFlowClient;

    @EJB
    private DataBrokerClient _dataBrokerClient;

    @EJB
    private DataFlowNodeLinkClient _dataFlowNodeLinkClient;
}
