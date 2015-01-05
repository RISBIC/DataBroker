/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.rdf.RDFMetadataContent;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.hp.hpl.jena.rdf.model.Resource;

public class RDFMutableMetadataContentSelector implements MetadataContentSelector
{
    public RDFMutableMetadataContentSelector(Resource resource)
    {
        _resource = resource;
    }

    @Override
    public MetadataContent getMetadataContent()
    {
        return new RDFMetadataContent(_resource);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataContentSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMutableMetadataContentSelector.class))
            return (S) this;
        else
            return null;
    }

    private Resource _resource;
}