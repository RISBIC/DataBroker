/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DataFlowEntity implements Serializable
{
    private static final long serialVersionUID = -7243768175433344968L;

    public DataFlowEntity()
    {
    }

    public DataFlowEntity(String id, Set<DataFlowNodeEntity> dataFlowNodes, Set<DataFlowLinkEntity> dataFlowLinks)
    {
        _id            = id;
        _dataFlowNodes = dataFlowNodes;
        _dataFlowLinks = dataFlowLinks;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public Set<DataFlowNodeEntity> getDataFlowNodes()
    {
        return _dataFlowNodes;
    }

    public void setDataFlowNodes(Set<DataFlowNodeEntity> dataFlowNodes)
    {
        _dataFlowNodes = dataFlowNodes;
    }

    public Set<DataFlowLinkEntity> getDataFlowLinks()
    {
        return _dataFlowLinks;
    }

    public void setDataFlowLinks(Set<DataFlowLinkEntity> dataFlowLinks)
    {
        _dataFlowLinks = dataFlowLinks;
    }

    @Id
    @Column(name = "id")
    protected String _id;

    @Column(name = "dataFlowNodes")
    @OneToMany(mappedBy = "_dataFlow", cascade = CascadeType.ALL)
    protected Set<DataFlowNodeEntity> _dataFlowNodes;

    @Column(name = "dataFlowLinks")
    @OneToMany(mappedBy = "_dataFlow", cascade = CascadeType.ALL)
    protected Set<DataFlowLinkEntity> _dataFlowLinks;
}
