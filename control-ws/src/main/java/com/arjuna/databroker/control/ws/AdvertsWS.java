/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

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
                logger.info("metadata blob id: " + metadataBlogIds);

                Map<String, Resource> resourceMap = new HashMap<String, Resource>();

                mapMetadataBlob(resourceMap, metadataBlogId);

                String       id           = UUID.randomUUID().toString();
                String       metadataId   = metadataBlogId;
                String       metadataPath = null;
                Boolean      rootNode     = null;
                String       nodeClass    = "Test";
                String       name         = null;
                String       summary      = null;
                String       discription  = null;
                Date         dataCreated  = null;
                Date         dateUpdate   = null;
                String       owner        = null;
                List<String> tags         = Collections.<String>emptyList();
                List<String> childNodeIds = Collections.<String>emptyList();

                AdvertNodeDTO advertNodeDTO = new AdvertNodeDTO(id, metadataId, metadataPath, rootNode, nodeClass, name, summary, discription, dataCreated, dateUpdate, owner, tags, childNodeIds);

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

    private static final String[] knownRootNodeTypeURIs = { "http://rdfs.arjuna.com/datasource#DataSource" };

    private void mapMetadataBlob(Map<String, Resource> resourceMap, String metadataBlogId)
    {
        try
        {
            String content = _metadataContentStore.getContent(metadataBlogId);

            Model  model  = ModelFactory.createDefaultModel();
            Reader reader = new StringReader(content);
            model.read(reader, null);
            reader.close();

            Property rdfTypeProperty = model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type");

            for (String knownRootNodeTypeURI: knownRootNodeTypeURIs)
            {
                logger.info("node type: " + knownRootNodeTypeURI);

                Property     knownRootNodeTypeProperty = model.getProperty(knownRootNodeTypeURI);
                StmtIterator statements                = model.listStatements(null, rdfTypeProperty, knownRootNodeTypeProperty);

                while (statements.hasNext())
                {
                    Statement statement = statements.nextStatement();
                    resourceMap.put(UUID.randomUUID().toString(), statement.getResource());
                    logger.info("statement: " + statement.asTriple().toString());
                }
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to map metadata blob", throwable);
        }
    }

    @EJB
    private MetadataContentStore _metadataContentStore;

    @EJB
    private AccessControlUtils _accessControlUtils;
}
