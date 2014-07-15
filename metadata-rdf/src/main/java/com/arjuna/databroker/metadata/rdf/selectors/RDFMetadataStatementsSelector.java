/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class RDFMetadataStatementsSelector implements MetadataStatementsSelector
{
    public RDFMetadataStatementsSelector(Resource resource)
    {
        _resource = resource;
    }

    @Override
    public MetadataStatementSelector statement(String name, String type)
    {
        Model    model    = _resource.getModel();
        Property property = model.getProperty(name);

        return new RDFMetadataStatementSelector(_resource.getProperty(property));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataStatementsSelector> S selector(Class<S> c) throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataStatementsSelector.class))
            return (S) this;
        else
            return null;
    }

    private Resource _resource;
}