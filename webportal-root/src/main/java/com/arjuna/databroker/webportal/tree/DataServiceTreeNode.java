/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class DataServiceTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = -5713567605573809197L;

    private static final String TYPE = "Data Service";

    public DataServiceTreeNode()
    {
    }

    public DataServiceTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property producesDataSetProperty = model.getProperty("http://rdfs.arjuna.com/datasource#", "producesDataSet");

        if (producesDataSetProperty.equals(statement.getPredicate()))
            return new DataSetTreeNode(model, statement.getResource());
        else
            return null;
    }
}
