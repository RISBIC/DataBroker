/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.Metadata;
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
    public MetadataSelector parent()
    {
        return null;
    }

    @Override
    public MetadataSelector description()
    {
        return null;
    }

    @Override
    public MetadatasSelector children()
    {
        return null;
    }

    @Override
    public MetadataContentsSelector contents()
    {
        return null;
    }

    @Override
    public <T extends MetadataSelector> T selector(Class<T> c)
        throws IllegalArgumentException
    {
        return null;
    }

    @Override
    public Metadata getMetadata()
    {
        return null;
    }

    private RDFMetadata _metadata;
}