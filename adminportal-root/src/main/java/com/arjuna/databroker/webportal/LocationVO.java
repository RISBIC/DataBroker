/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;

public class LocationVO implements Serializable
{
    private static final long serialVersionUID = 2400294141291623071L;

    public LocationVO()
    {
    }

    public LocationVO(String name, Location location)
    {
        _name     = name;
        _location = location;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public Location getLocation()
    {
        return _location;
    }

    public void setLocation(Location location)
    {
        _location = location;
    }

    public String doToPage()
    {
        return _location.doToPage();
    }

    private String   _name;
    private Location _location;
}
