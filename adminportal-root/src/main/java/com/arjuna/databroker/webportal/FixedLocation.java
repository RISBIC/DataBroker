/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

public class FixedLocation implements Location
{
    public FixedLocation(String page)
    {
        _page = page;
    }

    public String doToPage()
    {
        return _page;
    }

    private String _page;
}
