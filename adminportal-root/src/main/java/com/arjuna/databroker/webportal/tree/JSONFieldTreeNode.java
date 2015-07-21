/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class JSONFieldTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = 1901973938714006813L;

    private static final String TYPE = "JSON Field";

    public JSONFieldTreeNode()
    {
    }

    public JSONFieldTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property typeProperty = model.getProperty("http://rdfs.arjuna.com/json#", "hasField");

        if (typeProperty.equals(statement.getPredicate()))
            return new JSONFieldTreeNode(model, statement.getResource());
        else
            return null;
    }
}
