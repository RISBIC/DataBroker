/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name="metadatanodeedit")
public class MetadataNodeEditMO implements Serializable
{
    private static final long serialVersionUID = -3603881327332151804L;

    private static final Logger logger = Logger.getLogger(MetadataNodeEditMO.class.getName());

    public MetadataNodeEditMO()
    {
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getSummary()
    {
        return _summary;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getDetails()
    {
        return _details;
    }

    public void setDetails(String details)
    {
        _details = details;
    }

    public String doEdit(String serviceRootURL, String requesterId, String userId, String metadataId, String title, String summary, String details)
    {
        _title   = title;
        _summary = summary;
        _details = details;

        return "metadatanode_edit?faces-redirect=true";
    }

    public String doChangeSubmit()
    {
        return "metadatanode_edit_done?faces-redirect=true";
    }

    private String _title;
    private String _summary;
    private String _details;
}
