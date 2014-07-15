/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean(name="user")
public class User implements Serializable
{
    private static final long serialVersionUID = -8233388832031549211L;

    public User()
    {
    }

    public String getUsername()
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final Principal    principal    = facesContext.getExternalContext().getUserPrincipal();

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
    {
        final FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.getExternalContext().invalidateSession();

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
