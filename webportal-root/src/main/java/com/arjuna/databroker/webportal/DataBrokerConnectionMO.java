/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.store.DataBrokerEntity;
import com.arjuna.databroker.webportal.store.DataBrokerUtils;

@SessionScoped
@ManagedBean(name="databrokerconnection")
public class DataBrokerConnectionMO implements Serializable
{
    private static final long serialVersionUID = 4978082246302656043L;

    private static final Logger logger = Logger.getLogger(DataBrokerConnectionMO.class.getName());

    public DataBrokerConnectionMO()
    {
        _id             = null;
        _name           = "";
        _summary        = "";
        _serviceRootURL = "";
        _requesterId    = "";
        _errorMessage   = null;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getSummary()
    {
        return _summary;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
    }

    public String getRequesterId()
    {
        return _requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        _requesterId = requesterId;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doAdd()
    {
        clear();
        _errorMessage = null;

        return "/databrokers/databrokerconnection_add?faces-redirect=true";
    }

    public String doAddSubmit()
    {
        _dataBrokerUtils.createDataBroker(_name, _summary, _serviceRootURL, _requesterId);
        _errorMessage = null;

        return "/databrokers/databrokerconnection?faces-redirect=true";
    }

    public String doChange(String id)
    {
        load(id);

        return "/databrokers/databrokerconnection_change?faces-redirect=true";
    }

    public String doChangeSubmit()
    {
        if ((_id != null) && (! _id.equals("")))
            _dataBrokerUtils.replaceDataBroker(_id, _name, _summary, _serviceRootURL, _requesterId);
        else
            _errorMessage = "Unable to update information.";

        return "/databrokers/databrokerconnection?faces-redirect=true";
    }

    private void clear()
    {
        _id             = null;
        _name           = "";
        _summary        = "";
        _serviceRootURL = "";
        _requesterId    = "";
    }

    private void load(String id)
    {
        try
        {
            DataBrokerEntity dataBroker = null;

            _errorMessage = null;
            dataBroker = _dataBrokerUtils.retrieveDataBroker(id);

            clear();
            if (dataBroker != null)
            {
                _id             = dataBroker.getId();
                _name           = dataBroker.getName();
                _summary        = dataBroker.getSummary();
                _serviceRootURL = dataBroker.getServiceRootURL();
                _requesterId    = dataBroker.getRequesterId();
            }
            else if (_errorMessage == null)
                _errorMessage = "Unable to load information.";
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unexpected problem in 'load'", throwable);
            clear();
            _errorMessage = "Unexpected problem in 'load'";
        }
    }

    private String _id;
    private String _name;
    private String _summary;
    private String _serviceRootURL;
    private String _requesterId;
    private String _errorMessage;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;
}
