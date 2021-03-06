/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
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
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@Path("/metadata")
@Stateless
public class AdvertsWS
{
    private static final Logger logger = Logger.getLogger(AdvertsWS.class.getName());

    @GET
    @Path("/adverts/_ids")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getMetadataIds(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId)
    {
        logger.log(Level.FINE, "AdvertsWS.getMetadataIds: [" + requesterId + "][" + userId + "]");
        try
        {
            if ((requesterId == null) && (userId != null))
            {
                logger.log(Level.WARNING, "getMetadataIds: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "]");
                return Collections.emptyList();
            }

            return _accessControlUtils.listAccessable(requesterId, userId);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getMetadataIds: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @GET
    @Path("/adverts/_paths")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getMetadataRootPaths(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId, @QueryParam("metadataid") String metadataId)
    {
        logger.log(Level.FINE, "AdvertsWS.getMetadataRootPathIds: [" + requesterId + "][" + userId + "]");
        try
        {
            if (((requesterId == null) && (userId != null)) || (metadataId == null))
            {
                logger.log(Level.WARNING, "getMetadataRootPathIds: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "], metadataId=[" + metadataId + "]");
                return Collections.emptyList();
            }

            Map<String, AdvertNodeDTO> advertMap = new HashMap<String, AdvertNodeDTO>();

            scanMetadataBlob(metadataId, advertMap);

            return new LinkedList<String>(advertMap.keySet());
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getMetadataRootPathIds: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @GET
    @Path("/adverts/_all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AdvertNodeDTO> getAdverts(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId)
    {
        logger.log(Level.FINE, "AdvertsWS.getAdverts: [" + requesterId + "][" + userId + "]");
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
                Map<String, AdvertNodeDTO> advertMap = new HashMap<String, AdvertNodeDTO>();

                scanMetadataBlob(metadataBlogId, advertMap);

                result.addAll(advertMap.values());
            }

            return result;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getAdverts: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @GET
    @Path("/adverts/_blob")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AdvertNodeDTO> getAdverts(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId, @QueryParam("metadataid") String metadataId)
    {
        logger.log(Level.FINE, "AdvertsWS.getAdverts: [" + requesterId + "][" + userId + "][" + metadataId + "]");
        try
        {
            if (((requesterId == null) && (userId != null)) || (metadataId == null))
            {
                logger.log(Level.WARNING, "getAdverts: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "], metadataId=[" + metadataId + "]");
                return Collections.emptyList();
            }

            List<AdvertNodeDTO> result = new LinkedList<AdvertNodeDTO>();
            if (_accessControlUtils.canRead(metadataId, requesterId, userId))
            {
                Map<String, AdvertNodeDTO> advertMap = new HashMap<String, AdvertNodeDTO>();

                scanMetadataBlob(metadataId, advertMap);

                result.addAll(advertMap.values());
            }

            return result;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getAdverts: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @GET
    @Path("/adverts/_path")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AdvertNodeDTO> getAdverts(@QueryParam("requesterid") String requesterId, @QueryParam("userid") String userId, @QueryParam("metadataid") String metadataId, @QueryParam("metadatapath") String metadataPath)
    {
        logger.log(Level.FINE, "AdvertsWS.getAdverts: [" + requesterId + "][" + userId + "][" + metadataId + "][" + metadataPath + "]");
        try
        {
            if (((requesterId == null) && (userId != null)) || (metadataId == null) || (metadataPath == null))
            {
                logger.log(Level.WARNING, "getAdverts: Invalid parameters: requesterId=[" + requesterId + "], userId=[" + userId + "], metadataId=[" + metadataId + "], metadataPath=[" + metadataPath + "]");
                return Collections.emptyList();
            }

            if (_accessControlUtils.canRead(metadataId, requesterId, userId))
            {
                Map<String, AdvertNodeDTO> advertMap = new HashMap<String, AdvertNodeDTO>();

                searchMetadataPath(metadataId, metadataPath, advertMap);

                return new LinkedList<AdvertNodeDTO>(advertMap.values());
            }
            else
                return Collections.<AdvertNodeDTO>emptyList();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "getAdverts: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    private static final String[] knownRootNodeTypeURIs =
    {
        "http://rdfs.arjuna.com/datasource#DataSource",
        "http://rdfs.arjuna.com/jdbc/postgresql#Database",
        "http://rdfs.arjuna.com/xssf#Workbook",
        "http://rdfs.arjuna.com/json#Document"
    };

    private void scanMetadataBlob(String metadataBlogId, Map<String, AdvertNodeDTO> advertMap)
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
                Resource     knownRootNodeTypeResource = model.getResource(knownRootNodeTypeURI);
                StmtIterator statements                = model.listStatements(null, rdfTypeProperty, knownRootNodeTypeResource);

                while (statements.hasNext())
                {
                    Statement statement = statements.nextStatement();
                    Resource  subject   = statement.getSubject();

                    scanSubject(subject, metadataBlogId, true, advertMap);
                }
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to map metadata blob", throwable);
        }
    }

    private void searchMetadataPath(String metadataBlogId, String metadataPath, Map<String, AdvertNodeDTO> advertMap)
    {
        try
        {
            String content = _metadataContentStore.getContent(metadataBlogId);

            Model  model  = ModelFactory.createDefaultModel();
            Reader reader = new StringReader(content);
            model.read(reader, null);
            reader.close();

            Resource subject = model.getResource(metadataPath);

            scanSubject(subject, metadataBlogId, true, advertMap);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to map metadata path", throwable);
        }
    }

    private static final String[] knownChildPropertyURIs =
    {
        "http://rdfs.arjuna.com/datasource#hasDataService",
        "http://rdfs.arjuna.com/datasource#producesDataSet",
        "http://rdfs.arjuna.com/datasource#hasField",
        "http://rdfs.arjuna.com/access#hasAccessInfo",
        "http://rdfs.arjuna.com/jdbc/postgresql#hasDatabaseTable",
        "http://rdfs.arjuna.com/jdbc/postgresql#hasTableField",
        "http://rdfs.arjuna.com/xssf#hasSheet",
        "http://rdfs.arjuna.com/xssf#hasColumn",
        "http://rdfs.arjuna.com/json#hasContent",
        "http://rdfs.arjuna.com/json#hasField"
    };

    private AdvertNodeDTO scanSubject(Resource subject, String metadataBlogId, Boolean rootNode, Map<String, AdvertNodeDTO> advertMap)
    {
        if (! advertMap.containsKey(subject.getURI()))
        {
            AdvertNodeDTO advertNode = obtainAdvertNode(subject, metadataBlogId, rootNode, advertMap);

            for (String knownChildPropertyURI: knownChildPropertyURIs)
            {
                Property     knownChildProperty = subject.getModel().getProperty(knownChildPropertyURI);
                StmtIterator statements         = subject.getModel().listStatements(subject, knownChildProperty, (RDFNode) null);

                while (statements.hasNext())
                {
                    Statement statement    = statements.nextStatement();
                    Resource  childSubject = statement.getResource();

                    AdvertNodeDTO childAdvertNode = scanSubject(childSubject, metadataBlogId, false, advertMap);

                    advertNode.getChildNodeIds().add(childAdvertNode.getId());
                }
            }

            return advertNode;
        }
        else
            return obtainAdvertNode(subject, metadataBlogId, rootNode, advertMap);
    }

    private AdvertNodeDTO obtainAdvertNode(Resource subject, String metadataBlogId, Boolean rootNode, Map<String, AdvertNodeDTO> advertMap)
    {
        AdvertNodeDTO result = advertMap.get(subject.getURI());
        if (result == null)
        {
            result = createAdvertNode(subject, metadataBlogId, rootNode);
            advertMap.put(subject.getURI(), result);
        }
        else
            result.setRootNode(rootNode || result.getRootNode());

        return result;
    }

    private AdvertNodeDTO createAdvertNode(Resource subject, String metadataBlogId, Boolean rootNode)
    {
        AdvertNodeDTO advertNode = null;

        try
        {
            logger.log(Level.FINE, "createAdvertNode: subject - " + subject.getURI());

            Property     hasTitle             = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasTitle");
            Property     hasSummary           = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasSummary");
            Property     hasDetails           = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasDetails");
            Property     hasDateCreated       = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasDateCreated");
            Property     hasDateUpdated       = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasDateUpdated");
            Property     hasOwner             = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasOwner");
            Property     hasLocation          = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasLocation");
            Property     hasTag               = subject.getModel().getProperty("http://rdfs.arjuna.com/description#", "hasTag");
            Statement    titleStatement       = subject.getProperty(hasTitle);
            Statement    summaryStatement     = subject.getProperty(hasSummary);
            Statement    detailsStatement     = subject.getProperty(hasDetails);
            Statement    dateCreatedStatement = subject.getProperty(hasDateCreated);
            Statement    dateUpdatedStatement = subject.getProperty(hasDateUpdated);
            Statement    ownerStatement       = subject.getProperty(hasOwner);
            Statement    locationStatement    = subject.getProperty(hasLocation);
            StmtIterator tagStatements        = subject.getModel().listStatements(subject, hasTag, (RDFNode) null);

            String       id           = UUID.randomUUID().toString();
            String       metadataId   = metadataBlogId;
            String       metadataPath = subject.getURI();
            String       nodeClass    = null;
            String       name         = null;
            String       summary      = null;
            String       description  = null;
            Date         dateCreated  = null;
            Date         dateUpdated  = null;
            String       owner        = null;
            String       location     = null;
            List<String> tags         = new LinkedList<String>();
            List<String> childNodeIds = new LinkedList<String>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

            if (titleStatement != null)
                name = titleStatement.getString();
            if (summaryStatement != null)
                summary = summaryStatement.getString();
            if (detailsStatement != null)
                description = detailsStatement.getString();
            if (dateCreatedStatement != null)
                dateCreated = dateFormat.parse(dateCreatedStatement.getString());
            if (dateUpdatedStatement != null)
                dateUpdated = dateFormat.parse(dateUpdatedStatement.getString());
            if (ownerStatement != null)
                owner = ownerStatement.getString();
            if (locationStatement != null)
                location = locationStatement.getString();
            while ((tagStatements != null) && tagStatements.hasNext())
            {
                Statement tagStatement = tagStatements.nextStatement();
                if (tagStatement != null)
                    tags.add(tagStatement.getString());
            }

            advertNode = new AdvertNodeDTO(id, metadataId, metadataPath, rootNode, nodeClass, name, summary, description, dateCreated, dateUpdated, owner, location, tags, childNodeIds);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create advert", throwable);
        }

        return advertNode;
    }

    private void loadPropertyFiles()
    {
        try
        {
            File knownRootNodeTypesConfigFile = new File(System.getProperty("jboss.server.config.dir"), "knownrootnodetypes.conf");

            String[] knownRootNodeTypeURIs = loadPropertyLines(knownRootNodeTypesConfigFile);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Failed to load 'knownrootnodetypes.conf'", throwable);
        }

        try
        {
            File knownChildPropertiesConfigFile = new File(System.getProperty("jboss.server.config.dir"), "knownchildproperties.conf");

            String[] knownChildPropertyURIs = loadPropertyLines(knownChildPropertiesConfigFile);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Failed to load 'knownclildproperties.conf'", throwable);
        }
    }

    private String[] loadPropertyLines(File file)
    {
        try
        {
            List<String> lines =new LinkedList<String>();

            FileReader     fileReader     = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line != null)
            {
                line = line.trim();
                if (! "".equals(line))
                    lines.add(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

            return lines.toArray(new String[0]);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Failed to load Property Lines", throwable);
            return new String[0];
        }
    }

    @EJB
    private MetadataContentStore _metadataContentStore;

    @EJB
    private AccessControlUtils _accessControlUtils;
}

