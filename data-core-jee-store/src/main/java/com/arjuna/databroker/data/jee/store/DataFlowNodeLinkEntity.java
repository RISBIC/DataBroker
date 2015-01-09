/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DataFlowNodeLinkEntity implements Serializable
{
    private static final long serialVersionUID = 1636112437064485251L;

    public DataFlowNodeLinkEntity()
    {
    }

    public DataFlowNodeLinkEntity(String id, DataFlowNodeEntity nodeSource, DataFlowNodeEntity nodeSink, DataFlowEntity dataFlow)
    {
        _id         = id;
        _nodeSource = nodeSource;
        _nodeSink   = nodeSink;
        _dataFlow   = dataFlow;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public DataFlowNodeEntity getNodeSource()
    {
        return _nodeSource;
    }

    public void setNodeSource(DataFlowNodeEntity nodeSource)
    {
        _nodeSource = nodeSource;
    }

    public DataFlowNodeEntity getNodeSink()
    {
        return _nodeSink;
    }

    public void setNodeSink(DataFlowNodeEntity nodeSink)
    {
        _nodeSink = nodeSink;
    }

    public void setDataFlow(DataFlowEntity dataFlow)
    {
        _dataFlow = dataFlow;
    }

    public DataFlowEntity getDataFlow()
    {
        return _dataFlow;
    }

    @Id
    @Column(name = "id")
    protected String _id;

    @ManyToOne
    @JoinColumn(name = "nodeSource", nullable=false)
    protected DataFlowNodeEntity _nodeSource;

    @ManyToOne
    @JoinColumn(name = "nodeSink", nullable=false)
    protected DataFlowNodeEntity _nodeSink;

    @ManyToOne
    @JoinColumn(name="dataFlow", nullable=false)
    public DataFlowEntity _dataFlow;
}
