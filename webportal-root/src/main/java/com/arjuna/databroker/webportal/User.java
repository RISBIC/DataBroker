/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name="user")
public class User implements Serializable
{
    private static final long serialVersionUID = -8233388832031549211L;

    public User()
    {
        _username = "";
        _password = "";
    }

    public String getUsername()
    {
        return _username;
    }

    public void setUsername(String username)
    {
        _username = username;
    }

    public String getPassword()
    {
        return "********";
    }

    public void setPassword(String password)
    {
        _password = password;
    }

    public boolean isLoggedIn()
    {
        return _loggedIn;
    }

    public String doSignin()
    {
        _loggedIn = (! "".equals(_username)) && (! "".equals(_password)) && _username.equals(_password);
        
        if (_loggedIn)
            return "#";
        else
            return "#";
    }

    public String doSignout()
    {
        _username = "";
        _password = "";
        _loggedIn = false;
        
        return "#";
    }

    public String doSignup()
    {
        return "#";
    }

    private String  _username;
    private String  _password;
    private boolean _loggedIn;
}
