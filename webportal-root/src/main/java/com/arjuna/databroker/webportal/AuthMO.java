/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
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

        logger.log(Level.FINE, "getUsername: " + principal);

        if (principal != null)
            return principal.getName();
        else
            return "";
    }

    public void setUsername(String username)
    {
        logger.log(Level.FINE, "setUsername: [" + username + "]");
    }

    public String getPassword()
    {
        logger.log(Level.FINE, "getPassword");

        return "";
    }

    public void setPassword(String password)
    {
        logger.log(Level.FINE, "setPassword: [" + password + "]");
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
        logger.log(Level.FINE, "Auth.doSignin");

        return "#";
    }

    public String doSignout()
        throws IOException
    {
        logger.log(Level.FINE, "Auth.doSignout");

        final FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.getExternalContext().invalidateSession();

        facesContext.getExternalContext().redirect("/index.html");

        return "/index?faces-redirect=true";
    }

    public String doSignup()
    {
        logger.log(Level.FINE, "Auth.doSignup");

        return "#";
    }

    public String doSigndown()
    {
        logger.log(Level.FINE, "Auth.doSigndown");

        return "#";
    }
}
