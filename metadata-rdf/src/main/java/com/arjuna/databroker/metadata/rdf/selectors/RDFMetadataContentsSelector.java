/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.rdf.RDFMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;

public class RDFMetadataContentsSelector implements MetadataContentsSelector
{
    public RDFMetadataContentsSelector(RDFMetadata metadata)
    {
        _metadata = metadata;
    }

    public RDFMetadataContentSelector withPath(String path)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends MetadataContentsSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private RDFMetadata _metadata;
}