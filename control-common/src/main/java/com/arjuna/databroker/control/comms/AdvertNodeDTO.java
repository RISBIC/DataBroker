/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdvertNodeDTO implements Serializable
{
    private static final long serialVersionUID = 6504054762620758099L;

    public AdvertNodeDTO()
    {
        _id           = null;
        _metadataId   = null;
        _metadataPath = null;
        _rootNode     = null;
        _nodeClass    = null;
        _name         = null;
        _summary      = null;
        _discription  = null;
        _dateCreated  = null;
        _dateUpdate   = null;
        _owner        = null;
        _tags         = Collections.emptyList();
        _childNodeIds = Collections.emptyList();
    }

    public AdvertNodeDTO(String id, String metadataId, String metadataPath, Boolean rootNode, String nodeClass, String name, String summary, String discription, Date dataCreated, Date dateUpdate, String owner, List<String> tags, List<String> childNodeIds)
    {
        _id           = id;
        _metadataId   = metadataId;
        _metadataPath = metadataPath;
        _rootNode     = rootNode;
        _nodeClass    = nodeClass;
        _name         = name;
        _summary      = summary;
        _discription  = discription;
        _dateCreated  = dataCreated;
        _dateUpdate   = dateUpdate;
        _owner        = owner;
        _tags         = tags;
        _childNodeIds = childNodeIds;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
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

    public Boolean getRootNode()
    {
        return _rootNode;
    }

    public void setRootNode(Boolean rootNode)
    {
        _rootNode = rootNode;
    }

    public String getNodeClass()
    {
        return _nodeClass;
    }

    public void setNodeClass(String nodeClass)
    {
        _nodeClass = nodeClass;
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

    public String getDiscription()
    {
        return _discription;
    }

    public void setDiscription(String discription)
    {
        _discription = discription;
    }

    public Date getDateCreated()
    {
        return _dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        _dateCreated = dateCreated;
    }

    public Date getDateUpdate()
    {
        return _dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate)
    {
        _dateUpdate = dateUpdate;
    }

    public String getOwner()
    {
        return _owner;
    }

    public void setOwner(String owner)
    {
        _owner = owner;
    }

    public List<String> getTags()
    {
        return _tags;
    }

    public void setTags(List<String> tags)
    {
        _tags = tags;
    }

    public List<String> getChildNodeIds()
    {
        return _childNodeIds;
    }

    public void setChildNodeIds(List<String> childNodeIds)
    {
        _childNodeIds = childNodeIds;
    }

    private String       _id;
    private String       _metadataId;
    private String       _metadataPath;
    private Boolean      _rootNode;
    private String       _nodeClass;
    private String       _name;
    private String       _summary;
    private String       _discription;
    private Date         _dateCreated;
    private Date         _dateUpdate;
    private String       _owner;
    private List<String> _tags;
    private List<String> _childNodeIds;
}