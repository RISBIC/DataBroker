/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.io.Serializable;
import com.arjuna.databroker.webportal.comms.AdvertNodeSummary;

public class AdvertSummary implements Serializable
{
    private static final long serialVersionUID = -4422151347399658832L;

    public AdvertSummary()
    {
        _serviceURL   = null;
        _metadataId   = null;
        _metadataPath = null;
        _rootNode     = null;
        _node         = null;
    }

    public AdvertSummary(String serviceURL, String metadataId, String metadataPath, Boolean rootNode, AdvertNodeSummary node)
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

    public AdvertNodeSummary getNode()
    {
        return _node;
    }

    public void setNode(AdvertNodeSummary node)
    {
        _node = node;
    }

    private String            _serviceURL;
    private String            _metadataId;
    private String            _metadataPath;
    private Boolean           _rootNode;
    private AdvertNodeSummary _node;
}
