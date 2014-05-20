/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.DataFlowFactoryClient;

@SessionScoped
@ManagedBean(name="dataflowcreate")
public class DataFlowCreateMO implements Serializable
{
    private static final long serialVersionUID = 9127650311696520661L;

    private static final Logger logger = Logger.getLogger(DataFlowCreateMO.class.getName());

    public DataFlowCreateMO()
    {
        _serviceRootURL = null;
        _metaProperties = null;
        _name           = null;
        _properties     = null;
        _errorMessage   = null;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
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

    public String doLoad(String serviceRootURL)
    {
        logger.log(Level.FINE, "DataFlowCreateMO.doLoad: " + serviceRootURL);

        _serviceRootURL = serviceRootURL;

        _metaProperties = null;
        _name           = null;
        _properties     = null;
        _errorMessage   = null;
        if (_dataFlowFactoryClient != null)
        {
            List<String> metaPropertyNames = _dataFlowFactoryClient.getMetaPropertyNames(_serviceRootURL);

            if (! metaPropertyNames.isEmpty())
            {
                _metaProperties = new LinkedList<PropertyVO>();
                for (String metaPropertyName: metaPropertyNames)
                    _metaProperties.add(new PropertyVO(metaPropertyName, ""));
            }
            else
            {
                _metaProperties = new LinkedList<PropertyVO>();
                List<String> propertyNames = _dataFlowFactoryClient.getPropertyNames(_serviceRootURL, Collections.<String, String>emptyMap());

                _properties = new LinkedList<PropertyVO>();
                for (String propertyName: propertyNames)
                    _properties.add(new PropertyVO(propertyName, ""));
            }
        }
        else
            _errorMessage = "Unable to contact DataBroker";

        return "dataflow_create?faces-redirect=true";
    }

    public String doSubmit()
    {
        logger.log(Level.FINE, "DataFlowCreateMO.doSubmit");

        List<String> propertyNames = _dataFlowFactoryClient.getPropertyNames(_serviceRootURL, PropertyVO.toMap(_metaProperties));

        _properties = new LinkedList<PropertyVO>();
        for (String propertyName: propertyNames)
            _properties.add(new PropertyVO(propertyName, ""));

        return "dataflow_create?faces-redirect=true";
    }

    public String doReset()
    {
        logger.log(Level.FINE, "DataFlowCreateMO.doReset");

        _metaProperties = null;
        _name           = null;
        _properties     = null;
        _errorMessage   = null;
        if (_dataFlowFactoryClient != null)
        {
            List<String> metaPropertyNames = _dataFlowFactoryClient.getMetaPropertyNames(_serviceRootURL);

            if (! metaPropertyNames.isEmpty())
            {
                _metaProperties = new LinkedList<PropertyVO>();
                for (String metaPropertyName: metaPropertyNames)
                    _metaProperties.add(new PropertyVO(metaPropertyName, ""));
            }
            else
            {
                List<String> propertyNames = _dataFlowFactoryClient.getPropertyNames(_serviceRootURL, Collections.<String, String>emptyMap());

                if (! propertyNames.isEmpty())
                {
                    _properties = new LinkedList<PropertyVO>();
                    for (String propertyName: propertyNames)
                        _properties.add(new PropertyVO(propertyName, ""));
                }
            }
        }
        else
            _errorMessage = "Unable to contact DataBroker";

        return "dataflow_create?faces-redirect=true";
    }

    public String doCancel()
    {
        logger.log(Level.FINE, "DataFlowCreateMO.doCancel");

        return "databroker?faces-redirect=true";
    }

    public String doCreate()
    {
        logger.log(Level.FINE, "DataFlowCreateMO.doCreate");

        if (_dataFlowFactoryClient != null)
            _dataFlowFactoryClient.createDataFlow(_serviceRootURL, _name, PropertyVO.toMap(_metaProperties), PropertyVO.toMap(_properties));
        else
            _errorMessage = "Unable to contact DataBroker";

        return "dataflow_create_done?faces-redirect=true";
    }

    public String doDone()
    {
        logger.log(Level.FINE, "DataFlowCreateMO.doDone");

        return "databroker?faces-redirect=true";
    }

    private String           _serviceRootURL;
    private List<PropertyVO> _metaProperties;
    private String           _name;
    private List<PropertyVO> _properties;
    private String           _errorMessage;

    @EJB
    private DataFlowFactoryClient _dataFlowFactoryClient;
}
