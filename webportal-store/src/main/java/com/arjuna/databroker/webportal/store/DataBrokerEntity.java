/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class DataBrokerEntity implements Serializable
{
    private static final long serialVersionUID = 6595365591149062175L;

    public DataBrokerEntity()
    {
    }

    public DataBrokerEntity(String name, String summary, String serviceRootURL, String requesterId)
    {
        _name           = name;
        _summary        = summary;
        _serviceRootURL = serviceRootURL;
        _requesterId    = requesterId;
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

    public String getSummary()
    {
        return _summary;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
    }

    public String getRequesterId()
    {
        return _requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        _requesterId = requesterId;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    protected String _id;

    @Column(name = "name")
    private String _name;

    @Column(name = "summary")
    private String _summary;

    @Column(name = "serviceRootURL")
    private String _serviceRootURL;

    @Column(name = "requesterId")
    private String _requesterId;
}
