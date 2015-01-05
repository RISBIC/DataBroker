/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.store.AccessControlUtils;

@Path("/metadata")
@Stateless
public class MetadataWS
{
    private static final Logger logger = Logger.getLogger(MetadataWS.class.getName());

    @GET
    @Path("/contents")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listMetadata(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId)
    {
        logger.log(Level.FINE, "MetadataWS.listMetadata: [" + requesterId + "][" + userId + "]");
        try
        {
            if ((requesterId == null) && (userId != null))
            {
                logger.log(Level.WARNING, "listMetadata: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "]");
                return Collections.emptyList();
            }

            List<String> result = new LinkedList<String>();

            for (String accessableId: _accessControlUtils.listAccessable(requesterId, userId))
                result.add(accessableId.toString());

            return result;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "listMetadata: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @GET
    @Path("/content/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMetadata(@PathParam("id") String id, @QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId)
    {
        try
        {
            logger.log(Level.FINE, "MetadataWS.getMetadata [" + id + "][" + requesterId + "][" + userId + "]");
            if (_accessControlUtils.canRead(id, requesterId, userId))
            {
                String content = _metadataContentStore.getContent(id);
            
                if (content != null)
                    return content;
                else
                {
                    logger.log(Level.WARNING, "getMetadata: No content");
                    return "";
                }
            }
            else
            {
                logger.log(Level.WARNING, "getMetadata: Can't be read");
                return "";
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getMetadata: Problem in 'getMetadata'", throwable);
            return "";
        }
    }

    @POST
    @Path("/contents")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postMetadata(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId, String content)
    {
        logger.log(Level.FINE, "MetadataWS.postMetadata [" + requesterId + "][" + userId + "]");

        if ((requesterId == null) || (userId == null))
        {
            logger.log(Level.WARNING, "postMetadata: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "]");
            return "";
        }

        try
        {
            if (_accessControlUtils.canCreateChild(null, requesterId, userId))
                return _metadataContentStore.createChild(null, null, content);
            else
            {
                logger.log(Level.WARNING, "postMetadata: Can't be access");
                return "";
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "postMetadata: Problem in 'postMetadata'", throwable);
            return "";
        }
    }

    @POST
    @Path("/contents")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postMetadata(@QueryParam("parentid") String parentId, @QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId, String content)
    {
        logger.log(Level.FINE, "MetadataWS.postMetadata [" + parentId + "][" + requesterId + "][" + userId + "]");

        try
        {
            if ((parentId == null) || (requesterId == null) || (userId == null))
            {
                logger.log(Level.WARNING, "postMetadata: Invalid parameters: parentId=[" + parentId + "], requesterId=[" + requesterId + "], userId=[" + userId + "]");
                return "";
            }

            if (_accessControlUtils.canCreateChild(parentId, requesterId, userId))
                return _metadataContentStore.createChild(parentId, null, content);
            else
            {
                logger.log(Level.WARNING, "postMetadata: Can't be access");
                return "";
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "postMetadata: Problem in 'postMetadata'", throwable);
            return "";
        }
    }

    @PUT
    @Path("/content/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean putMetadata(@PathParam("id") String id, @QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId, String content)
    {
        logger.log(Level.FINE, "MetadataWS.putMetadata [" + id + "][" + requesterId + "][" + userId + "]");

        try
        {
            if (requesterId == null)
            {
                logger.log(Level.WARNING, "putMetadata: Invalid parameters: requesterId=[" + requesterId + "]");
                return false;
            }

            if (_accessControlUtils.canUpdate(id, requesterId, userId))
            {
                if (! _metadataContentStore.setContent(id, content))
                    logger.log(Level.WARNING, "putMetadata: Can't be replaced");
                else
                    return true;
            }
            else
                logger.log(Level.WARNING, "putMetadata: Can't be access");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "putMetadata: Problem in 'putMetadata'", throwable);
        }

        return false;
    }

    @EJB
    private MetadataContentStore _metadataContentStore;

    @EJB
    private AccessControlUtils _accessControlUtils;
}
