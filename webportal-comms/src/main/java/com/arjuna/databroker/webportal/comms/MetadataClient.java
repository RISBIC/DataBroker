/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

@Stateless
public class MetadataClient
{
    private static final Logger logger = Logger.getLogger(MetadataClient.class.getName());

    public List<String> listMetadata(String serviceRootURL, String requesterId, String userId)
    {
        List<String> metadataIds = null;
        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/contents");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);

                logger.log(Level.INFO, "MetaDataWS URL: \"" + request.getUri() + "\"");

                ClientResponse<List<String>> response = request.get(new GenericType<List<String>>() {});

                if (response.getStatus() == 200)
                    metadataIds = response.getEntity();
                else
                    logger.log(Level.WARNING, "Problem in 'listMetadata' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter for in 'listMetadata' getting content");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'listMetadata'", throwable);
        }

        return metadataIds;
    }

    public String getContent(String serviceRootURL, String requesterId, String userId, String metadataId)
    {
        String content = null;
        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null) && (metadataId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/content/{metadataid}");
                request.pathParameter("metadataid", metadataId);
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);

                ClientResponse<String> response = request.get(new GenericType<String>() {});

                if (response.getStatus() == 200)
                    content =  response.getEntity();
                else
                    logger.log(Level.WARNING, "Problem in 'getContent' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter for in 'getContent' getting content");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getContent'", throwable);
        }

        return content;
    }
}
