/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class FieldTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = 5422118121285858579L;

    private static final String TYPE = "Field";

    public FieldTreeNode()
    {
    }

    public FieldTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property typeProperty = model.getProperty("http://rdfs.arjuna.com/datasource#", "hasType");

        if (typeProperty.equals(statement.getPredicate()))
            return new TypeTreeNode(model, statement.getResource());
        else
            return null;
    }
}
