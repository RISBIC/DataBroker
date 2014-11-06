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
        _metadataId   = null;
        _metadataPath = null;
        _rootNode     = null;
        _node         = null;
    }

    public AdvertVO(String serviceURL, String metadataId, String metadataPath, Boolean rootNode, AdvertNodeVO node)
    {
        _serviceURL   = serviceURL;
        _metadataId   = metadataId;
        _metadataPath = metadataPath;
        _rootNode     = rootNode;
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

    public Boolean isRootNode()
    {
        return _rootNode;
    }

    public void setRootNode(Boolean rootNode)
    {
        _rootNode = rootNode;
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
    private String       _metadataId;
    private String       _metadataPath;
    private Boolean      _rootNode;
    private AdvertNodeVO _node;
}
