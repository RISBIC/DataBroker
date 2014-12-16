/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
// import org.hibernate.annotations.GenericGenerator;

@Entity
public class DataFlowNodeEntity implements Serializable
{
    private static final long serialVersionUID = 6585424677180130492L;

    public DataFlowNodeEntity()
    {
    }

    public DataFlowNodeEntity(String id, DataFlowNodeEntity parent, DataFlowNodeEntity description, String content)
    {
        _id           = id;
        _parent       = parent;
        _description  = description;
        _content      = content;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public void setParent(DataFlowNodeEntity parent)
    {
        _parent = parent;
    }

    public DataFlowNodeEntity getParent()
    {
        return _parent;
    }

    public void setDescription(DataFlowNodeEntity description)
    {
        _description = description;
    }

    public DataFlowNodeEntity getDescription()
    {
        return _description;
    }

    public void setContent(String content)
    {
        _content = content;
    }

    public String getContent()
    {
        return _content;
    }

    @Id
    @Column(name = "id")
    protected String _id;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "parent")
    protected DataFlowNodeEntity _parent;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description")
    protected DataFlowNodeEntity _description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    protected String _content;
}
