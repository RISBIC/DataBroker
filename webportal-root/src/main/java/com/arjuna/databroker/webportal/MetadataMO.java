/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.MetadataClient;
import com.arjuna.databroker.webportal.tree.AbstractTreeNode;
import com.arjuna.databroker.webportal.tree.DataSourceTreeNode;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@SessionScoped
@ManagedBean(name="metadata")
public class MetadataMO implements Serializable
{
    private static final long serialVersionUID = 224847249676841536L;

    private static final Logger logger = Logger.getLogger(MetadataMO.class.getName());

    public MetadataMO()
    {
        _rootNodes = new LinkedList<AbstractTreeNode>();
    }

    public List<AbstractTreeNode> getRootNodes()
    {
        return _rootNodes;
    }

    public String doLoad(String serviceRootURL, String requesterId, String userId, String metadataId)
    {
        logger.log(Level.INFO, "MetadataMO.doLoad: " + serviceRootURL + ", " + requesterId + ", " + userId + ", " + metadataId);
        try
        {
            String content = _metadataClient.getContent(serviceRootURL, requesterId, userId, metadataId);

            _model = ModelFactory.createDefaultModel();
            Reader reader = new StringReader(content);
            _model.read(reader, null);
            reader.close();

            Property rdfTypeProperty    = _model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type");
            Property dataSourceProperty = _model.getProperty("http://rdfs.arjuna.com/datasource#", "DataSource");

            StmtIterator statements = _model.listStatements(null, rdfTypeProperty, dataSourceProperty);

            _rootNodes = new LinkedList<AbstractTreeNode>();
            while (statements.hasNext())
            {
                Statement statement = statements.nextStatement();
                _rootNodes.add(new DataSourceTreeNode(_model, statement.getSubject()));
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem generating Tree", throwable);
            _rootNodes = Collections.emptyList();
            _rootNodes = Collections.emptyList();
        }

        return "metadata?faces-redirect=true";
    }

    private Model _model;

    private List<AbstractTreeNode> _rootNodes;

    @EJB
    private MetadataClient _metadataClient;
}
