/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.control.comms.AdvertNodeDTO;
import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.store.AccessControlUtils;

@Path("/metadata")
@Stateless
public class AdvertsWS
{
    private static final Logger logger = Logger.getLogger(AdvertsWS.class.getName());

    @GET
    @Path("/adverts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AdvertNodeDTO> getAdverts(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId)
    {
        logger.log(Level.FINE, "AdvertsWS.listMetadata: [" + requesterId + "][" + userId + "]");
        try
        {
            if ((requesterId == null) && (userId != null))
            {
                logger.log(Level.WARNING, "getAdverts: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "]");
                return Collections.emptyList();
            }

            List<AdvertNodeDTO> result          = new LinkedList<AdvertNodeDTO>();
            List<String>        metadataBlogIds = _accessControlUtils.listAccessable(requesterId, userId);
 
            for (String metadataBlogId: metadataBlogIds)
            {
                String id = UUID.randomUUID().toString();

                AdvertNodeDTO advertNodeDTO = new AdvertNodeDTO(id, "Test", metadataBlogId, null, null, null, null, null, Collections.<String>emptyList(), Collections.<String>emptyList());

                result.add(advertNodeDTO);
            }

            return result;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getAdverts: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @EJB
    private MetadataContentStore _metadataContentStore;

    @EJB
    private AccessControlUtils _accessControlUtils;
}
