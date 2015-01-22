/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.arjuna.databroker.webportal.comms.MetadataClient;

@SessionScoped
@ManagedBean(name="dataview")
public class DataViewMO implements Serializable
{
    private static final long serialVersionUID = 5925161013129278224L;

    private static final Logger logger = Logger.getLogger(DataViewMO.class.getName());

    public DataViewMO()
    {
        _serviceRootURL = null;
        _requesterId    = null;
        _userId         = null;
        _metadataIds    = new LinkedList<String>();
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

    public String getRequesterId()
    {
        return _requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        _requesterId = requesterId;
    }

    public String getUserId()
    {
        return _userId;
    }

    public void setUserId(String userId)
    {
        _userId = userId;
    }

    public List<String> getMetadataIds()
    {
        return _metadataIds;
    }

    public void setMetadataIds(List<String> metadataIds)
    {
        _metadataIds = metadataIds;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doLoad(String serviceRootURL, String requesterId, String userId)
    {
        logger.log(Level.FINE, "DataViewMO.doLoad: " + serviceRootURL + ", " + requesterId + ", " + userId);

        _serviceRootURL = serviceRootURL;
        _requesterId    = requesterId;
        _userId         = userId;

        load();

        return "/dataviews/dataview?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "DataViewMO.doReload: " + _serviceRootURL + ", " + _requesterId + ", " + _userId);

        load();

        return "#";
    }

    public String doToPageReload()
    {
        logger.log(Level.FINE, "DataViewMO.doToPageReload: " + _serviceRootURL + ", " + _requesterId + ", " + _userId);

        load();

        return "/dataviews/dataview?faces-redirect=true";
    }

    public void load()
    {
        logger.log(Level.FINE, "DataViewMO.load");
        try
        {
            _metadataIds.clear();
            _metadataIds.addAll(_metadataClient.listMetadata(_serviceRootURL, _requesterId, _userId));
            _errorMessage = null;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem getting metadata ids", throwable);
            _metadataIds.clear();
            _errorMessage = "Problem getting metadata ids";
        }
    }

    private String       _serviceRootURL;
    private String       _requesterId;
    private String       _userId;
    private List<String> _metadataIds;
    private String       _errorMessage;

    @EJB
    private MetadataClient _metadataClient;
}
