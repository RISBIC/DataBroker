/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.rdf.RDFMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;

public class RDFMetadataContentSelector implements MetadataContentSelector
{
    public RDFMetadataContentSelector(RDFMetadata metadata)
    {
        _metadata = metadata;
    }

    public RDFMetadataContentSelector withPath(String path)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends MetadataContentSelector> T selector(Class<T> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private RDFMetadata _metadata;
}