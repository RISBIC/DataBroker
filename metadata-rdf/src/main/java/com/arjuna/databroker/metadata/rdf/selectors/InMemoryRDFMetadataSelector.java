/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.rdf.RDFMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class InMemoryRDFMetadataSelector implements MetadataSelector
{
    public InMemoryRDFMetadataSelector(RDFMetadata metadata)
    {
        _metadata = metadata;
    }

    public MetadataSelector parent()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public MetadataSelector description()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public MetadatasSelector children()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public MetadataContentsSelector contents()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public <T extends MetadataSelector> T selector(Class<T> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Metadata getMetadata()
    {
        return _metadata;
    }

    private RDFMetadata _metadata;
}