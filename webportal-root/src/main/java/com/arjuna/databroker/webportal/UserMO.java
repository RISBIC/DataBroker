/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.store.UserEntity;
import com.arjuna.databroker.webportal.store.UsersUtils;

@SessionScoped
@ManagedBean(name="user")
public class UserMO implements Serializable
{
	private static final long serialVersionUID = 1291911620957873665L;

	private static final Logger logger = Logger.getLogger(UserMO.class.getName());

    public UserMO()
    {
        _userName     = "";
        _password     = "";
        _errorMessage = null;
    }

    public String getUserName()
    {
        return _userName;
    }

    public void setUserName(String userName)
    {
        _userName = userName;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String password)
    {
    	_password = password;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doAdd()
    {
        clear();
        _errorMessage = null;

        return "/users/user_add?faces-redirect=true";
    }

    public String doAddSubmit()
    {
    	_usersUtils.createUser(_userName, _password);
        _errorMessage = null;

        return "/users/user?faces-redirect=true";
    }

    public String doChange(String id)
    {
        load(id);

        return "/users/user_change?faces-redirect=true";
    }

    public String doChangeSubmit()
    {
        if ((_userName != null) && (! _userName.equals("")))
        	_usersUtils.replaceUser(_userName, _password);
        else
            _errorMessage = "Unable to update information.";

        return "/users/user?faces-redirect=true";
    }

    private void clear()
    {
        _userName = "";
        _password = "";
    }

    private void load(String id)
    {
        try
        {
            UserEntity user = null;

            _errorMessage = null;
            user = _usersUtils.retrieveUser(id);

            clear();
            if (user != null)
            {
            	_userName = user.getUserName();
                _password = user.getPassword();
            }
            else if (_errorMessage == null)
                _errorMessage = "Unable to load information.";
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unexpected problem in 'load'", throwable);
            clear();
            _errorMessage = "Unexpected problem in 'load'";
        }
    }

    private String _userName;
    private String _password;
    private String _errorMessage;

    @EJB
    private UsersUtils _usersUtils;
}
