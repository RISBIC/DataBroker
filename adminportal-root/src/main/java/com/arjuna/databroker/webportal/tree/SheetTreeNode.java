/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class SheetTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = -4909923120031455330L;

    private static final String TYPE = "Sheet";

    public SheetTreeNode()
    {
    }

    public SheetTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property hasColumnProperty = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasColumn");

        if (hasColumnProperty.equals(statement.getPredicate()))
            return new ColumnTreeNode(model, statement.getResource());
        else
            return null;
    }
}
