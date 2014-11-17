/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;

public class AdvertVO implements Serializable
{
    private static final long serialVersionUID = -5027867116260479886L;

    public AdvertVO()
    {
        _serviceURL   = null;
        _requesterId  = null;
        _metadataId   = null;
        _metadataPath = null;
        _isRootNode   = null;
        _node         = null;
    }

    public AdvertVO(String serviceURL, String requesterId, String metadataId, String metadataPath, Boolean isRootNode, AdvertNodeVO node)
    {
        _serviceURL   = serviceURL;
        _requesterId  = requesterId;
        _metadataId   = metadataId;
        _metadataPath = metadataPath;
        _isRootNode   = isRootNode;
        _node         = node;
    }

    public String getServiceURL()
    {
        return _serviceURL;
    }

    public void setServiceURL(String serviceURL)
    {
        _serviceURL = serviceURL;
    }

    public String getRequesterId()
    {
        return _requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        _requesterId = requesterId;
    }

    public String getMetadataId()
    {
        return _metadataId;
    }

    public void setMetadataId(String metadataId)
    {
        _metadataId = metadataId;
    }

    public String getMetadataPath()
    {
        return _metadataPath;
    }

    public void setMetadataPath(String metadataPath)
    {
        _metadataPath = metadataPath;
    }

    public Boolean getIsRootNode()
    {
        return _isRootNode;
    }

    public void setIsRootNode(Boolean isRootNode)
    {
        _isRootNode = isRootNode;
    }

    public AdvertNodeVO getNode()
    {
        return _node;
    }

    public void setNode(AdvertNodeVO node)
    {
        _node = node;
    }

    private String       _serviceURL;
    private String       _requesterId;
    private String       _metadataId;
    private String       _metadataPath;
    private Boolean      _isRootNode;
    private AdvertNodeVO _node;
}
