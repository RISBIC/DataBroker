/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter("/*")
public class SignInFilter implements Filter
{
    private static final Logger logger = Logger.getLogger(SignInFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        logger.info("SignInFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws ServletException, IOException
    {
        logger.info("SignInFilter.filter: [" + request.getServletContext().getContextPath() + "]");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Object             user        = httpRequest.getSession().getAttribute("user");

        if (user != null)
            chain.doFilter(request, response);
        else
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/signin.xhtml");
        }
    }

    @Override
    public void destroy()
    {
        logger.info("SignInFilter.destroy");
    } 
}
