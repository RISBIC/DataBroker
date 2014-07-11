/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.rdf.RDFMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class RDFMetadataSelector implements MetadataSelector
{
    public RDFMetadataSelector(RDFMetadata metadata)
    {
        _metadata = metadata;
    }

    @Override
    public Metadata getMetadata()
    {
        return _metadata;
    }

    @Override
    public MetadataSelector parent()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadatasSelector children()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadataSelector description()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadataContentsSelector contents()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataSelector.class))
            return (S) this;
        else
            return null;
    }

    private RDFMetadata _metadata;
}