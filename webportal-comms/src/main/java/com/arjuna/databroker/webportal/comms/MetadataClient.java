/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.resteasy.util.HttpResponseCodes;

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

                logger.log(Level.FINE, "MetaDataWS URL: \"" + request.getUri() + "\"");

                ClientResponse<List<String>> response = request.get(new GenericType<List<String>>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
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

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                    content =  response.getEntity();
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

        return content;
    }

    public boolean setContent(String serviceRootURL, String requesterId, String userId, String metadataId, String content)
    {
        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null) && (metadataId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/content/{metadataid}");
                request.pathParameter("metadataid", metadataId);
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);
                request.body(MediaType.TEXT_PLAIN, content);

                ClientResponse<Boolean> response = request.put(new GenericType<Boolean>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                    return response.getEntity();
                else
                    logger.log(Level.WARNING, "Problem in 'setContent' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'setContent' for setting content");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'setContent'", throwable);
        }

        return false;
    }
}
