/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.store;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class AccessControlEntity implements Serializable
{
    private static final long serialVersionUID = 6595365591149062175L;

    public AccessControlEntity()
    {
    }

    public AccessControlEntity(MetadataEntity metadata, String requesterId, String userId, Boolean canList, Boolean canRead, Boolean canUpdate, Boolean canRemove, Boolean canCreateChild, Boolean canChangeAccess)
    {
        _metadata        = metadata;
        _requesterId     = requesterId;
        _userId          = userId;
        _canList         = canList;
        _canRead         = canRead;
        _canUpdate       = canUpdate;
        _canRemove       = canRemove;
        _canCreateChild  = canCreateChild;
        _canChangeAccess = canChangeAccess;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public MetadataEntity getMetadata()
    {
        return _metadata;
    }

    public void setMetadata(MetadataEntity metadata)
    {
        _metadata = metadata;
    }

    public String getRequesterId()
    {
        return _requesterId;
    }

    public void setRequester(String requesterId)
    {
        _requesterId = requesterId;
    }

    public String getUserId()
    {
        return _userId;
    }

    public void setUserId(String userId)
    {
        _userId = userId;
    }

    public Boolean getCanList()
    {
        return _canList;
    }

    public void setCanList(Boolean canList)
    {
        _canList = canList;
    }

    public Boolean getCanRead()
    {
        return _canRead;
    }

    public void setCanRead(Boolean canRead)
    {
        _canRead = canRead;
    }

    public Boolean getCanUpdate()
    {
        return _canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate)
    {
        _canUpdate = canUpdate;
    }

    public Boolean getCanRemove()
    {
        return _canRemove;
    }

    public void setCanRemove(Boolean canRemove)
    {
        _canRemove = canRemove;
    }

    public Boolean getCanCreateChild()
    {
        return _canCreateChild;
    }

    public void setCanCreateChild(Boolean canCreateChild)
    {
        _canCreateChild = canCreateChild;
    }

    public Boolean getCanChangeAccess()
    {
        return _canChangeAccess;
    }

    public void setCanChangeAccess(Boolean canChangeAccess)
    {
        _canChangeAccess = canChangeAccess;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    protected String _id;

    @JoinColumn(name = "metadata")
    @ManyToOne(fetch = FetchType.LAZY)
    protected MetadataEntity _metadata;

    @Column(name = "requesterId")
    protected String _requesterId;

    @Column(name = "userId")
    protected String _userId;

    @Column(name = "canList")
    protected Boolean _canList;

    @Column(name = "canRead")
    protected Boolean _canRead;

    @Column(name = "canUpdate")
    protected Boolean _canUpdate;

    @Column(name = "canRemove")
    protected Boolean _canRemove;

    @Column(name = "canCreateChild")
    protected Boolean _canCreateChild;

    @Column(name = "canChangeAccess")
    protected Boolean _canChangeAccess;
}
