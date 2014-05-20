/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;

public class DataBrokerConnectionVO implements Serializable
{
    private static final long serialVersionUID = 4384414923929789797L;

    public DataBrokerConnectionVO()
    {
    }

    public DataBrokerConnectionVO(String id, String name, String summary, String serviceRootURL, String requesterId)
    {
        _id             = id;
        _name           = name;
        _summary        = summary;
        _serviceRootURL = serviceRootURL;
        _requesterId    = requesterId;
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

    private String _id;
    private String _name;
    private String _summary;
    private String _serviceRootURL;
    private String _requesterId;
}
