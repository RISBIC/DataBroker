/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Collections;
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
        _metadataIds = new LinkedList<String>();
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

    public String doLoad(String serviceRootURL, String requesterId, String userId)
    {
        logger.log(Level.INFO, "DataViewMO.doLoad: " + serviceRootURL + ", " + requesterId + ", " + userId);
        try
        {
            _serviceRootURL = serviceRootURL;
            _requesterId    = requesterId;
            _userId         = userId;

            _metadataIds = _metadataClient.listMetadata(serviceRootURL, requesterId, userId);
            logger.log(Level.INFO, "DataViewMO.doLoad: " + _metadataIds);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem getting metadata ids", throwable);
            _metadataIds = Collections.emptyList();
        }

        return "dataview?faces-redirect=true";
    }

    private String       _serviceRootURL;
    private String       _requesterId;
    private String       _userId;
    private List<String> _metadataIds;

    @EJB
    private MetadataClient _metadataClient;
}
