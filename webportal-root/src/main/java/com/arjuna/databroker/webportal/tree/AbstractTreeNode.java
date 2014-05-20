/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public abstract class AbstractTreeNode implements Serializable
{
    private static final long serialVersionUID = -2547957704127983232L;

    public AbstractTreeNode()
    {
    }

    public AbstractTreeNode(Model model, Resource resource)
    {
        _model    = model;
        _resource = resource;

        Property  hadTitle       = _model.getProperty("http://rdfs.arjuna.com/description#", "hasTitle");
        Statement titleStatement = _resource.getProperty(hadTitle);

        if (titleStatement != null)
            _name = titleStatement.getString();
        else
            _name = "unknown";

        _subNodes = new LinkedList<AbstractTreeNode>();
        StmtIterator statements = _model.listStatements(resource, (Property) null, (RDFNode) null);
        while (statements.hasNext())
        {
            Statement statement = statements.nextStatement();

            AbstractTreeNode subNode = processSubNodeStatement(model, statement);
            if (subNode != null)
                _subNodes.add(subNode);
        }
    }

    public String getName()
    {
        return _name;
    }

    public List<AbstractTreeNode> getSubNodes()
    {
        return _subNodes;
    }

    public abstract String getType();

    public abstract AbstractTreeNode processSubNodeStatement(Model model, Statement statement);

    public Model getModel()
    {
        return _model;
    }

    public Resource getResource()
    {
        return _resource;
    }

    private Model    _model;
    private Resource _resource;

    private String                 _name;
    private List<AbstractTreeNode> _subNodes;
}
