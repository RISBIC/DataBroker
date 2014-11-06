/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdvertNodeSummary implements Serializable
{
    private static final long serialVersionUID = -7691582217386469513L;

    public AdvertNodeSummary()
    {
        _nodeClass   = null;
        _name        = null;
        _summary     = null;
        _discription = null;
        _dateCreated = null;
        _dateUpdate  = null;
        _owner       = null;
        _tags        = Collections.emptyList();
        _childNodes  = Collections.emptyList();
    }

    public AdvertNodeSummary(String nodeClass, String name, String summary, String discription, Date dataCreated, Date dateUpdate, String owner, List<String> tags, List<AdvertNodeSummary> childNodes)
    {
        _nodeClass   = nodeClass;
        _name        = name;
        _summary     = summary;
        _discription = discription;
        _dateCreated = dataCreated;
        _dateUpdate  = dateUpdate;
        _owner       = owner;
        _tags        = tags;
        _childNodes  = childNodes;
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

    public List<AdvertNodeSummary> getChildNodes()
    {
        return _childNodes;
    }

    public void setChildNodes(List<AdvertNodeSummary> childNodes)
    {
        _childNodes = childNodes;
    }

    private String                  _nodeClass;
    private String                  _name;
    private String                  _summary;
    private String                  _discription;
    private Date                    _dateCreated;
    private Date                    _dateUpdate;
    private String                  _owner;
    private List<String>            _tags;
    private List<AdvertNodeSummary> _childNodes;
}
