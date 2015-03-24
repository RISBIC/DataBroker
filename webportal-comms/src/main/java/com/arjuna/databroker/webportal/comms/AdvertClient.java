/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
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

    public List<String> getMetadataIds(String serviceRootURL, String requesterId, String userId)
    {
        List<String> metadataIds = new LinkedList<String>();

        logger.log(Level.FINE, "AdvertClient.getMetadataIds: serviceRootURL=[" + serviceRootURL + "], requesterId=[" + requesterId + "], userId=[" + userId + "]");

        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts/_ids");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);

                ClientResponse<List<String>> response = request.get(new GenericType<List<String>>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                {
                    metadataIds = response.getEntity();

                    logger.log(Level.FINE, "Received 'getMetadataIds' number " + metadataIds.size());
                }
                else
                    logger.log(Level.WARNING, "Problem in 'getMetadataIds' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'getMetadataIds' for getting adverts");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getMetadataIds'", throwable);
        }

        return metadataIds;
    }

    public List<String> getMetadataRootPaths(String serviceRootURL, String requesterId, String userId, String metadataId)
    {
        List<String> paths = new LinkedList<String>();

        logger.log(Level.FINE, "AdvertClient.getMetadataRootPaths: serviceRootURL=[" + serviceRootURL + "], requesterId=[" + requesterId + "], userId=[" + userId + "], metadataId=[" + metadataId + "]");

        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts/_paths");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);
                request.queryParameter("metadataid", metadataId);

                ClientResponse<List<String>> response = request.get(new GenericType<List<String>>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                {
                    paths = response.getEntity();

                    logger.log(Level.FINE, "Received 'getMetadataRootPaths' number " + paths.size());
                }
                else
                    logger.log(Level.WARNING, "Problem in 'getMetadataRootPaths' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'getMetadataRootPaths' for getting adverts");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getMetadataRootPaths'", throwable);
        }

        return paths;
    }

    public List<AdvertNodeSummary> getAdverts(String serviceRootURL, String requesterId, String userId)
    {
        List<AdvertNodeSummary> adverts = new LinkedList<AdvertNodeSummary>();

        logger.log(Level.FINE, "AdvertClient.getAdverts: serviceRootURL=[" + serviceRootURL + "], requesterId=[" + requesterId + "], userId=[" + userId + "]");

        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts/_all");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);

                ClientResponse<List<AdvertNodeDTO>> response = request.get(new GenericType<List<AdvertNodeDTO>>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                {
                    List<AdvertNodeDTO> advertNodeDTOs = response.getEntity();

                    logger.log(Level.FINE, "Received (all) 'adverts' number " + advertNodeDTOs.size());

                    for (AdvertNodeDTO advertNodeDTO: advertNodeDTOs)
                        adverts.add(new AdvertNodeSummary(advertNodeDTO.getId(), advertNodeDTO.getMetadataId(), advertNodeDTO.getMetadataPath(), advertNodeDTO.getRootNode(), advertNodeDTO.getNodeClass(), advertNodeDTO.getName(), advertNodeDTO.getSummary(), advertNodeDTO.getDescription(), advertNodeDTO.getDateCreated(), advertNodeDTO.getDateUpdate(), advertNodeDTO.getOwner(), advertNodeDTO.getTags(), advertNodeDTO.getChildNodeIds()));
                }
                else
                    logger.log(Level.WARNING, "Problem in 'getAdverts' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'getAdverts' for getting adverts");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getAdverts'", throwable);
        }

        return adverts;
    }

    public List<AdvertNodeSummary> getAdverts(String serviceRootURL, String requesterId, String userId, String metadataId)
    {
        List<AdvertNodeSummary> adverts = new LinkedList<AdvertNodeSummary>();

        logger.log(Level.FINE, "AdvertClient.getAdverts: serviceRootURL=[" + serviceRootURL + "], requesterId=[" + requesterId + "], userId=[" + userId + "], metadataId=[" + metadataId + "]");

        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts/_blob");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);
                request.queryParameter("metadataid", metadataId);

                ClientResponse<List<AdvertNodeDTO>> response = request.get(new GenericType<List<AdvertNodeDTO>>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                {
                    List<AdvertNodeDTO> advertNodeDTOs = response.getEntity();

                    logger.log(Level.FINE, "Received (blog) 'adverts' number " + advertNodeDTOs.size());

                    for (AdvertNodeDTO advertNodeDTO: advertNodeDTOs)
                        adverts.add(new AdvertNodeSummary(advertNodeDTO.getId(), advertNodeDTO.getMetadataId(), advertNodeDTO.getMetadataPath(), advertNodeDTO.getRootNode(), advertNodeDTO.getNodeClass(), advertNodeDTO.getName(), advertNodeDTO.getSummary(), advertNodeDTO.getDescription(), advertNodeDTO.getDateCreated(), advertNodeDTO.getDateUpdate(), advertNodeDTO.getOwner(), advertNodeDTO.getTags(), advertNodeDTO.getChildNodeIds()));
                }
                else
                    logger.log(Level.WARNING, "Problem in 'getAdverts' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'getAdverts' for getting adverts");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getAdverts'", throwable);
        }

        return adverts;
    }

    public List<AdvertNodeSummary> getAdverts(String serviceRootURL, String requesterId, String userId, String metadataId, String metadataPath)
    {
        List<AdvertNodeSummary> adverts = new LinkedList<AdvertNodeSummary>();

        logger.log(Level.FINE, "AdvertClient.getAdverts: serviceRootURL=[" + serviceRootURL + "], requesterId=[" + requesterId + "], userId=[" + userId + "], metadataId=[" + metadataId + "], metadataPath=[" + metadataPath + "]");

        try
        {
            if ((serviceRootURL != null) && (requesterId != null) && (userId != null))
            {
                ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/metadata/adverts/_path");
                request.queryParameter("requesterid", requesterId);
                request.queryParameter("userId", userId);
                request.queryParameter("metadataid", metadataId);
                request.queryParameter("metadatapath", metadataPath);

                ClientResponse<List<AdvertNodeDTO>> response = request.get(new GenericType<List<AdvertNodeDTO>>() {});

                if (response.getStatus() == HttpResponseCodes.SC_OK)
                {
                    List<AdvertNodeDTO> advertNodeDTOs = response.getEntity();

                    logger.log(Level.FINE, "Received (path) 'adverts' number " + advertNodeDTOs.size());

                    for (AdvertNodeDTO advertNodeDTO: advertNodeDTOs)
                        adverts.add(new AdvertNodeSummary(advertNodeDTO.getId(), advertNodeDTO.getMetadataId(), advertNodeDTO.getMetadataPath(), advertNodeDTO.getRootNode(), advertNodeDTO.getNodeClass(), advertNodeDTO.getName(), advertNodeDTO.getSummary(), advertNodeDTO.getDescription(), advertNodeDTO.getDateCreated(), advertNodeDTO.getDateUpdate(), advertNodeDTO.getOwner(), advertNodeDTO.getTags(), advertNodeDTO.getChildNodeIds()));
                }
                else
                    logger.log(Level.WARNING, "Problem in 'getAdverts' getting entity " + response.getStatus());
            }
            else
                logger.log(Level.WARNING, "Invalid parameter in 'getAdverts' for getting adverts");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'getAdverts'", throwable);
        }

        return adverts;
    }
}
