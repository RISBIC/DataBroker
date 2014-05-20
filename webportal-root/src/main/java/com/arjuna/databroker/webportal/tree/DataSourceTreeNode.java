/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class DataSourceTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = 5613737687374980737L;

    private static final String TYPE = "Data Source";
    
    public DataSourceTreeNode()
    {
    }

    public DataSourceTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property hasDataSourceProperty = model.getProperty("http://rdfs.arjuna.com/datasource#", "hasDataService");

        if (hasDataSourceProperty.equals(statement.getPredicate()))
            return new DataServiceTreeNode(model, statement.getResource());
        else
            return null;
    }
}
