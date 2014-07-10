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
    public <T extends MetadataSelector> T selector(Class<T> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private RDFMetadata _metadata;
}