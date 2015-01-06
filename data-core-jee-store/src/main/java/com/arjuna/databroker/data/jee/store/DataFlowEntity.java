/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@Entity
public class DataFlowEntity implements Serializable
{
    private static final long serialVersionUID = -7243768175433344968L;

    public DataFlowEntity()
    {
    }

    public DataFlowEntity(String id, String name, Map<String, String> properties, Set<DataFlowNodeEntity> dataFlowNodes, Set<DataFlowNodeLinkEntity> dataFlowNodeLinks)
    {
        _id                = id;
        _name              = name;
        _properties        = properties;
        _dataFlowNodes     = dataFlowNodes;
        _dataFlowNodeLinks = dataFlowNodeLinks;
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

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    public Set<DataFlowNodeEntity> getDataFlowNodes()
    {
        return _dataFlowNodes;
    }

    public void setDataFlowNodes(Set<DataFlowNodeEntity> dataFlowNodes)
    {
        _dataFlowNodes = dataFlowNodes;
    }

    public Set<DataFlowNodeLinkEntity> getDataFlowNodeLinks()
    {
        return _dataFlowNodeLinks;
    }

    public void setDataFlowNodeLinks(Set<DataFlowNodeLinkEntity> dataFlowNodeLinks)
    {
        _dataFlowNodeLinks = dataFlowNodeLinks;
    }

    @Id
    @Column(name = "id")
    protected String _id;

    @Column(name = "name")
    protected String _name;

    @Type(type = "serializable")
    @Column(name = "properties")
    protected Map<String, String> _properties;

    @Column(name = "dataFlowNodes")
    @OneToMany(mappedBy = "_dataFlow", cascade = CascadeType.ALL)
    protected Set<DataFlowNodeEntity> _dataFlowNodes;

    @Column(name = "dataFlowNodeLinks")
    @OneToMany(mappedBy = "_dataFlow", cascade = CascadeType.ALL)
    protected Set<DataFlowNodeLinkEntity> _dataFlowNodeLinks;
}
