/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name="navigation")
public class NavigationMO implements Serializable
{
    private static final long serialVersionUID = 4384414923929789797L;

    public NavigationMO()
    {
        _locations = new LinkedList<LocationVO>();

        _locations.add(new LocationVO("Home", new FixedLocation("/index?faces-redirect=true")));
        _locations.add(new LocationVO("Search", new FixedLocation("/search/searchhome?faces-redirect=true")));
        _locations.add(new LocationVO("Create", new FixedLocation("/create/createhome?faces-redirect=true")));
        _locations.add(new LocationVO("Config", new FixedLocation("/config/confighome?faces-redirect=true")));
    }

    public List<LocationVO> getLocations()
    {
        return _locations;
    }

    public String doGoTo(String name)
    {
        Location location = null;

        ListIterator<LocationVO> locationIterator = _locations.listIterator();

        while (locationIterator.hasNext())
        {
            LocationVO currentLocation = locationIterator.next();

            if ((location == null) && name.equals(currentLocation.getName()))
                location = currentLocation.getLocation();

//            if (page != null)
//                locationIterator.remove();
//            else if (name.equals(currentLocation.getName()))
//                page = currentLocation.getPage();
        }

        if (location != null)
            return location.doToPage();
        else
            return "#";
    }

    private List<LocationVO> _locations;
}
