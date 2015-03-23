/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class WorkbookTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = 7309594721033164968L;

    private static final String TYPE = "Workbook";

    public WorkbookTreeNode()
    {
    }

    public WorkbookTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        Property hasSheetProperty = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasSheet");

        if (hasSheetProperty.equals(statement.getPredicate()))
            return new SheetTreeNode(model, statement.getResource());
        else
            return null;
    }
}
