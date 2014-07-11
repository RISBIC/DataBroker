/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.hp.hpl.jena.rdf.model.Model;

public class RDFMetadataContentsSelector implements MetadataContentsSelector
{
    public RDFMetadataContentsSelector(Model model)
    {
        _model = model;
    }

    public RDFMetadataContentSelector withPath(String path)
    {
        return new RDFMetadataContentSelector(_model.getResource(path));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataContentsSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataContentsSelector.class))
            return (S) this;
        else
            return null;
    }

    private Model _model;
}