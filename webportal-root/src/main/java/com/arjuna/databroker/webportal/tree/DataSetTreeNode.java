/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class DataSetTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = -8068773931011789403L;

    private static final String TYPE = "Data Set";

    public DataSetTreeNode()
    {
    }

    public DataSetTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property fieldProperty = model.getProperty("http://rdfs.arjuna.com/datasource#", "hasField");

        if (fieldProperty.equals(statement.getPredicate()))
            return new FieldTreeNode(model, statement.getResource());
        else
            return null;
    }
}
