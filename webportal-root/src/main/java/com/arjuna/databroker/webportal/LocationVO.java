/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;

public class LocationVO implements Serializable
{
    private static final long serialVersionUID = 2400294141291623071L;

    public LocationVO()
    {
    }

    public LocationVO(String name, String page, String iconClass)
    {
        _name = name;
        _page = page;
        _iconClass = iconClass;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getPage()
    {
        return _page;
    }

    public void setPage(String page)
    {
        _page = page;
    }

    public String getIconClass()
    {
        return _iconClass;
    }

    public void setIconClass(String iconClass)
    {
        _iconClass = iconClass;
    }

    private String _name;
    private String _page;
    private String _iconClass;
}
