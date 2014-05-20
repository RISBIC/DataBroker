/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.tree;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class TypeTreeNode extends AbstractTreeNode
{
    private static final long serialVersionUID = 4141114617441346416L;

    private static final String TYPE = "Type";

    public TypeTreeNode()
    {
    }

    public TypeTreeNode(Model model, Resource resource)
    {
        super(model, resource);
    }

    public String getType()
    {
        return TYPE;
    }

    public AbstractTreeNode processSubNodeStatement(Model model, Statement statement)
    {
        return null;
    }
}
