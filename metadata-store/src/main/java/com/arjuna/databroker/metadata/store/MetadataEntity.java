/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.store;

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
public class MetadataEntity implements Serializable
{
    private static final long serialVersionUID = 6585424677180130492L;

    public MetadataEntity()
    {
    }

    public MetadataEntity(String id, MetadataEntity parent, MetadataEntity description, String content)
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

    public void setParent(MetadataEntity parent)
    {
        _parent = parent;
    }

    public MetadataEntity getParent()
    {
        return _parent;
    }

    public void setDescription(MetadataEntity description)
    {
        _description = description;
    }

    public MetadataEntity getDescription()
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
    protected MetadataEntity _parent;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description")
    protected MetadataEntity _description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    protected String _content;
}
