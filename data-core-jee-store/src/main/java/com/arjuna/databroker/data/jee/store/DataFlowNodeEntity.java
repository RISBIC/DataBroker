/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

@Entity
public class DataFlowNodeEntity implements Serializable
{
    private static final long serialVersionUID = 7120653672640034870L;

    public DataFlowNodeEntity()
    {
    }

    public DataFlowNodeEntity(String id, String name, Map<String, String> properties, String nodeClassName, DataFlowEntity dataFlow, Serializable state)
    {
        _id         = id;
        _name       = name;
        _properties = properties;
        _className  = nodeClassName;
        _dataFlow   = dataFlow;
        _state      = state;
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

    public String getClassName()
    {
        return _className;
    }

    public void setClassName(String className)
    {
        _className = className;
    }

    public DataFlowEntity getDataFlow()
    {
        return _dataFlow;
    }

    public void setDataFlow(DataFlowEntity dataFlow)
    {
        _dataFlow = dataFlow;
    }

    public Serializable getState()
    {
        return _state;
    }

    public void setState(Serializable state)
    {
        _state = state;
    }

    @Id
    @Column(name = "id")
    protected String _id;

    @Column(name = "name")
    protected String _name;

    @Type(type = "serializable")
    @Column(name = "properties")
    protected Map<String, String> _properties;

    @Column(name = "className")
    protected String _className;

    @ManyToOne
    @JoinColumn(name="dataFlow", nullable=false)
    protected DataFlowEntity _dataFlow;

    @Column(name = "state")
    protected Serializable _state;
}
