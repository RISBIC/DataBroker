/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
public class AdvertClient
{
    private static final Logger logger = Logger.getLogger(AdvertClient.class.getName());

    public List<AdvertNodeSummary> getAdverts(String serviceRootURL, String requesterId, String userId)
    {
        List<AdvertNodeSummary> adverts = new LinkedList<AdvertNodeSummary>();

        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);

                ClientResponse<String> response = request.get(new GenericType<String>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                {
                    @SuppressWarnings("unused")
                    String content = response.getEntity(); // TODO
                }
                else
                    logger.log(Level.WARNING, "Problem in 'getContent' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'getContent' for getting content");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getContent'", throwable);
        }

        return adverts;
    }
}
