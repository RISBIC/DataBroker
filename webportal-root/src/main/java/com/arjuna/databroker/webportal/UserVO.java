/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;

public class UserVO implements Serializable
{
    private static final long serialVersionUID = 1268654505288208143L;

    public UserVO()
    {
    }

    public UserVO(String name, Boolean active, List<String> roles)
    {
        _name   = name;
        _active = active;
        _roles  = roles;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public Boolean getActive()
    {
        return _active;
    }

    public void setActive(Boolean active)
    {
        _active = active;
    }

    public List<String> getRoles()
    {
        return _roles;
    }

    public void setRoles(List<String> roles)
    {
        _roles = roles;
    }

    private String       _name;
    private Boolean      _active;
    private List<String> _roles;
}
