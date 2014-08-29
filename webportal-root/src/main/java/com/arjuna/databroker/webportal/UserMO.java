/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
        _userName      = "";
        _password      = "";
        _activeAccount = Boolean.FALSE;
        _adminRole     = Boolean.FALSE;
        _userRole      = Boolean.TRUE;
        _guestRole     = Boolean.TRUE;
        _errorMessage  = null;
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
        return "";
    }

    public void setPassword(String password)
    {
        _password = password;
    }

    public Boolean getActiveAccount()
    {
        return _activeAccount;
    }

    public void setActiveAccount(Boolean activeAccount)
    {
        _activeAccount = activeAccount;
    }

    public Boolean getAdminRole()
    {
        return _adminRole;
    }

    public void setAdminRole(Boolean adminRole)
    {
        _adminRole = adminRole;
    }

    public Boolean getUserRole()
    {
        return _userRole;
    }

    public void setUserRole(Boolean userRole)
    {
        _userRole = userRole;
    }

    public Boolean getGuestRole()
    {
        return _guestRole;
    }

    public void setGuestRole(Boolean guestRole)
    {
        _guestRole = guestRole;
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
        try
        {
            if ((_password != null) && _password.trim().equals(""))
                _password = null;
            _usersUtils.createUser(_userName, _password, _adminRole, _userRole, _guestRole);
            _activeAccount = (_password != null);
            _errorMessage  = null;
        }
        catch (EJBException ejbException)
        {
            if (ejbException.getCausedByException() instanceof IllegalArgumentException)
                _errorMessage = ejbException.getCausedByException().getMessage();
            else
                _errorMessage = ejbException.getCausedByException().toString();
            _activeAccount = Boolean.FALSE;
        }
        catch (Throwable throwable)
        {
            _errorMessage  = "Problem: " + throwable.toString();
            _activeAccount = Boolean.FALSE;
        }
 
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
        {
            try
            {
                if ((_password != null) && _password.trim().equals(""))
                    _password = null;
                _usersUtils.changeUser(_userName, _password, _adminRole, _userRole, _guestRole);
                _activeAccount = (_password != null);
                _errorMessage  = null;
            }
            catch (EJBException ejbException)
            {
                if (ejbException.getCausedByException() instanceof IllegalArgumentException)
                    _errorMessage = ejbException.getCausedByException().getMessage();
                else
                    _errorMessage = ejbException.getCausedByException().toString();
                _activeAccount = Boolean.FALSE;
            }
            catch (Throwable throwable)
            {
                _errorMessage = "Problem: " + throwable.toString();
                _activeAccount = Boolean.FALSE;
            }
        }
        else
            _errorMessage = "Unable to update information.";

        return "/users/user?faces-redirect=true";
    }

    private void clear()
    {
        _userName      = "";
        _password      = "";
        _activeAccount = Boolean.FALSE;
        _adminRole     = Boolean.FALSE;
        _userRole      = Boolean.TRUE;
        _guestRole     = Boolean.TRUE;
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
                _userName      = user.getUserName();
                _activeAccount = (user.getPassword() != null);
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

    private String  _userName;
    private String  _password;
    private Boolean _activeAccount;
    private Boolean _adminRole;
    private Boolean _userRole;
    private Boolean _guestRole;
    private String  _errorMessage;

    @EJB
    private UsersUtils _usersUtils;
}
