/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@ManagedBean(name="auth")
public class AuthMO implements Serializable
{
    private static final long serialVersionUID = -8233388832031549211L;

    private static final Logger logger = Logger.getLogger(AuthMO.class.getName());

    public AuthMO()
    {
    }

    public String getUsername()
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final Principal    principal    = facesContext.getExternalContext().getUserPrincipal();

        logger.log(Level.FINER, "getUsername: " + principal);

        if (principal != null)
            return principal.getName();
        else
            return "";
    }

    public void setUsername(String username)
    {
        logger.log(Level.FINER, "setUsername: [" + username + "]");

        _username = username;
    }

    public String getPassword()
    {
        logger.log(Level.FINER, "getPassword");

        return "";
    }

    public void setPassword(String password)
    {
        logger.log(Level.FINER, "setPassword: [" + password + "]");

        _password = password;
    }

    public boolean isInRole(String rolename)
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();

        return facesContext.getExternalContext().isUserInRole(rolename);
    }

    public boolean isLoggedIn()
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();

        return facesContext.getExternalContext().getUserPrincipal() != null;
    }

    public String doSignin()
        throws IOException
    {
        logger.log(Level.FINER, "Auth.doSignin");

        final FacesContext       facesContext = FacesContext.getCurrentInstance();
        final HttpServletRequest request      = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        try
        {
            request.login(_username, _password);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Auth.doSignin: failed: " + throwable);
            logger.log(Level.FINEST, "Throwable: ", throwable);
        }
        _username = null;
        _password = null;

        return "#";
    }

    public String doSignout()
        throws IOException
    {
        logger.log(Level.FINER, "Auth.doSignout");

        final FacesContext       facesContext = FacesContext.getCurrentInstance();
        final HttpServletRequest request      = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        try
        {
            request.logout();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Auth.doSignout: failed: " + throwable);
            logger.log(Level.FINEST, "Throwable: ", throwable);
        }

//        facesContext.getExternalContext().redirect("/index.html");

        return "/index?faces-redirect=true";
    }

    public String doSignup()
    {
        logger.log(Level.FINER, "Auth.doSignup");

        return "#";
    }

    public String doSigndown()
    {
        logger.log(Level.FINER, "Auth.doSigndown");

        return "#";
    }

    private String _username;
    private String _password;
}
