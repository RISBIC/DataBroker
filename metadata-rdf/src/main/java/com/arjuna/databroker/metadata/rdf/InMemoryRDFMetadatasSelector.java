/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Collection;
import java.util.Map;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class InMemoryRDFMetadatasSelector implements MetadatasSelector
{
    public InMemoryRDFMetadatasSelector(Map<String, RDFMetadata> metadataMap)
    {
        _metadataMap = metadataMap;
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends MetadatasSelector> T selector(Class<T> c) throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Metadata> getMetadatas()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private Map<String, RDFMetadata> _metadataMap;
}