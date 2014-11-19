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
import com.arjuna.databroker.control.comms.AdvertNodeDTO;

@Stateless
public class AdvertClient
{
    private static final Logger logger = Logger.getLogger(AdvertClient.class.getName());

    public List<AdvertNodeSummary> getAdverts(String serviceRootURL, String requesterId, String userId) {
        List<AdvertNodeSummary> adverts = new LinkedList<AdvertNodeSummary>();

        try {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null)) {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);

                ClientResponse<List<AdvertNodeDTO>> response = request.get(new GenericType<List<AdvertNodeDTO>>() {
                });

                if (response.getStatus() == HttpResponseCodes.SC_OK) {
                    List<AdvertNodeDTO> advertNodeDTOs = response.getEntity();

                    logger.log(Level.FINE, "Received 'adverts' number " + advertNodeDTOs.size());

                    for (AdvertNodeDTO advertNodeDTO : advertNodeDTOs)
                        adverts.add(new AdvertNodeSummary(advertNodeDTO.getId(), advertNodeDTO.getMetadataId(), advertNodeDTO.getMetadataPath(), advertNodeDTO.getRootNode(), advertNodeDTO.getNodeClass(), advertNodeDTO.getName(), advertNodeDTO.getSummary(), advertNodeDTO.getDiscription(), advertNodeDTO.getDateCreated(), advertNodeDTO.getDateUpdate(), advertNodeDTO.getOwner(), advertNodeDTO.getTags(), advertNodeDTO.getChildNodeIds()));
                } else
                    logger.log(Level.WARNING, "Problem in 'getAdverts' getting entity " + response.getStatus());
            } else
                logger.log(Level.WARNING, "Invalid parameter in 'getAdverts' for getting adverts");
        } catch (Throwable throwable) {
            logger.log(Level.WARNING, "Problem in 'getAdverts'", throwable);
        }

        return adverts;
    }
}
