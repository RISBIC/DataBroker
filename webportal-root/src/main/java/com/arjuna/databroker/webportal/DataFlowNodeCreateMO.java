/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.DataFlowClient;
import com.arjuna.databroker.webportal.comms.DataFlowNodeFactorySummary;
import com.arjuna.databroker.webportal.comms.RequestProblemException;

@SessionScoped
@ManagedBean(name="dataflownodecreate")
public class DataFlowNodeCreateMO implements Serializable
{
    private static final long serialVersionUID = 3195884544692435118L;

    private static final Logger logger = Logger.getLogger(DataFlowNodeCreateMO.class.getName());

    public DataFlowNodeCreateMO()
    {
        _serviceRootURL    = null;
        _dataFlowId        = null;
        _type              = null;
        _factoryNames      = null;
        _factoryName       = null;
        _factoryProperties = null;
        _metaProperties    = null;
        _name              = null;
        _properties        = null;
        _errorMessage      = null;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
    }

    public String getDataFlowId()
    {
        return _dataFlowId;
    }

    public void setDataFlowId(String dataFlowId)
    {
        _dataFlowId = dataFlowId;
    }

    public String getType()
    {
        return _type;
    }

    public void setType(String type)
    {
        _type = type;
    }

    public List<String> getFactoryNames()
    {
        return _factoryNames;
    }

    public void setFactoryNames(List<String> factoryNames)
    {
        _factoryNames = factoryNames;
    }

    public String getFactoryName()
    {
        return _factoryName;
    }

    public void setFactoryName(String factoryName)
    {
        _factoryName = factoryName;
    }

    public List<PropertyVO> getFactoryProperties()
    {
        return _factoryProperties;
    }

    public void setFactoryProperties(List<PropertyVO> factoryProperties)
    {
        _factoryProperties = factoryProperties;
    }

    public List<PropertyVO> getMetaProperties()
    {
        return _metaProperties;
    }

    public void setMetaProperties(List<PropertyVO> metaProperties)
    {
        _metaProperties = metaProperties;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public List<PropertyVO> getProperties()
    {
        return _properties;
    }

    public void setProperties(List<PropertyVO> properties)
    {
        _properties = properties;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doLoad(String serviceRootURL, String dataFlowId, String type)
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doLoad: " + serviceRootURL);

        _serviceRootURL = serviceRootURL;
        _dataFlowId     = dataFlowId;
        _type           = type;

        _factoryNames      = null;
        _factoryName       = null;
        _factoryProperties = null;
        _metaProperties    = null;
        _name              = null;
        _properties        = null;
        _errorMessage      = null;

        if (_dataFlowClient != null)
        {
            try
            {
                _factoryNames = _dataFlowClient.getFactoryNames(_serviceRootURL, _dataFlowId, _type);
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }

            if ((_factoryNames != null) && (_factoryNames.size() == 1))
                _factoryName = _factoryNames.get(0);

            if (_factoryName != null)
            {
                List<String> metaPropertyNames = Collections.emptyList();
                try
                {
                    metaPropertyNames = _dataFlowClient.getMetaPropertyNames(_serviceRootURL, _dataFlowId, _type, _factoryName);
                }
                catch (RequestProblemException requestProblemException)
                {
                    _errorMessage = requestProblemException.getMessage();
                }

                if (! metaPropertyNames.isEmpty())
                {
                    _metaProperties = new LinkedList<PropertyVO>();
                    for (String metaPropertyName: metaPropertyNames)
                        _metaProperties.add(new PropertyVO(metaPropertyName, ""));
                }
                else
                {
                    _metaProperties = new LinkedList<PropertyVO>();
                    List<String> propertyNames = Collections.emptyList();
                    try
                    {
                        propertyNames = _dataFlowClient.getPropertyNames(_serviceRootURL, _dataFlowId, _type, _factoryName, Collections.<String, String>emptyMap());
                    }
                    catch (RequestProblemException requestProblemException)
                    {
                        _errorMessage = requestProblemException.getMessage();
                    }

                    _properties = new LinkedList<PropertyVO>();
                    for (String propertyName: propertyNames)
                        _properties.add(new PropertyVO(propertyName, ""));
                }
            }
        }
        else
            _errorMessage = "Unable to contact DataBroker";

        return "/dataflows/dataflownode_create?faces-redirect=true";
    }

    public String doFactoryNameSubmit()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doFactoryNameSubmit");

        List<String> metaPropertyNames = Collections.emptyList();
        try
        {
            DataFlowNodeFactorySummary dataFlowNodeFactorySummary = _dataFlowClient.getFactoryInfo(_serviceRootURL, _dataFlowId, _factoryName);
            _factoryName       = dataFlowNodeFactorySummary.getName();
            _factoryProperties = new LinkedList<PropertyVO>();
            for (Entry<String, String> property: dataFlowNodeFactorySummary.getProperties().entrySet())
                _factoryProperties.add(new PropertyVO(property.getKey(), property.getValue()));

            metaPropertyNames = _dataFlowClient.getMetaPropertyNames(_serviceRootURL, _dataFlowId, _type, _factoryName);
        }
        catch (RequestProblemException requestProblemException)
        {
            _errorMessage = requestProblemException.getMessage();
        }

        if (! metaPropertyNames.isEmpty())
        {
            _metaProperties = new LinkedList<PropertyVO>();
            for (String metaPropertyName: metaPropertyNames)
                _metaProperties.add(new PropertyVO(metaPropertyName, ""));
        }
        else
        {
            _metaProperties = new LinkedList<PropertyVO>();
            List<String> propertyNames = Collections.emptyList();
            try
            {
                propertyNames = _dataFlowClient.getPropertyNames(_serviceRootURL, _dataFlowId, _type, _factoryName, Collections.<String, String>emptyMap());
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }

            _properties = new LinkedList<PropertyVO>();
            for (String propertyName: propertyNames)
                _properties.add(new PropertyVO(propertyName, ""));
        }

        return "/dataflows/dataflownode_create?faces-redirect=true";
    }

    public String doMetaPropertiesSubmit()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doMetaPropertiesSubmit");

        List<String> propertyNames = Collections.emptyList();
        try
        {
            propertyNames = _dataFlowClient.getPropertyNames(_serviceRootURL, _dataFlowId, _type, _factoryName, listToMap(_metaProperties));
        }
        catch (RequestProblemException requestProblemException)
        {
            _errorMessage = requestProblemException.getMessage();
        }

        _properties = new LinkedList<PropertyVO>();
        for (String propertyName: propertyNames)
            _properties.add(new PropertyVO(propertyName, ""));

        return "/dataflows/dataflownode_create?faces-redirect=true";
    }

    public String doFactoryNameReset()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doFactoryNameReset");

        return doLoad(_serviceRootURL, _dataFlowId, _type);
    }

    public String doMetaPropertiesReset()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doMetaPropertiesReset");

        return doLoad(_serviceRootURL, _dataFlowId, _type);
    }

    public String doCancel()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doCancel");

        return "/dataflows/dataflow?faces-redirect=true";
    }

    public String doCreate()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doCreate");

        if (_dataFlowClient != null)
        {
            try
            {
                _dataFlowClient.createDataFlowNode(_serviceRootURL, _dataFlowId, _type, _factoryName, listToMap(_metaProperties), _name, listToMap(_properties));
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
        }
        else
            _errorMessage = "Unable to contact DataBroker";

        return "/dataflows/dataflownode_create_done?faces-redirect=true";
    }

    public String doDone()
    {
        logger.log(Level.FINE, "DataFlowNodeCreateMO.doDone");

        return "/dataflows/dataflow?faces-redirect=true";
    }

    private Map<String, String> listToMap(List<PropertyVO> properties)
    {
        if (properties != null)
        {
            Map<String, String> map = new HashMap<String, String>();

            for (PropertyVO property: properties)
                map.put(property.getName(), property.getValue());

            return map;
        }
        else
            return Collections.emptyMap();
    }

    private String           _serviceRootURL;
    private String           _dataFlowId;
    private String           _type;
    private List<String>     _factoryNames;
    private String           _factoryName;
    private List<PropertyVO> _factoryProperties;
    private List<PropertyVO> _metaProperties;
    private String           _name;
    private List<PropertyVO> _properties;
    private String           _errorMessage;

    @EJB
    private DataFlowClient _dataFlowClient;
}
