/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementsSelector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class RDFMutableMetadataStatementsSelector extends RDFMetadataStatementsSelector implements MutableMetadataStatementsSelector
{
    public RDFMutableMetadataStatementsSelector(Resource resource)
    {
        super(resource);
    }

    @Override
    public MutableMetadataStatementSelector mutableStatement(String name, String type)
    {
        Model    model    = _resource.getModel();
        Property property = model.getProperty(name);

        return new RDFMutableMetadataStatementSelector(_resource.getProperty(property));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MutableMetadataStatementsSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMutableMetadataStatementsSelector.class))
            return (S) this;
        else
            return null;
    }
}