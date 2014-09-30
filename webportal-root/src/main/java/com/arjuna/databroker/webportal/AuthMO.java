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

    public String getPassword()
    {
        return "";
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

    public String doSignout()
        throws IOException
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.getExternalContext().invalidateSession();

        facesContext.getExternalContext().redirect("/index.html");

        return "/index?faces-redirect=true";
    }

    public String doSignup()
    {
        return "#";
    }

    public String doSigndown()
    {
        return "#";
    }
}
