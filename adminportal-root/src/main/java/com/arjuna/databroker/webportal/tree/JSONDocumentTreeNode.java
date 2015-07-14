/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class JSONDocumentTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = -2535882373803946117L;

    private static final String TYPE = "JSON Document";

    public JSONDocumentTreeNode()
    {
    }

    public JSONDocumentTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property hasContentProperty = model.getProperty("http://rdfs.arjuna.com/json#", "hasContent");

        if (hasContentProperty.equals(statement.getPredicate()))
            return new JSONFieldTreeNode(model, statement.getResource());
        else
            return null;
    }
}
