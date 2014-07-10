/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import java.util.Map;
import com.arjuna.databroker.metadata.rdf.RDFMetadata;
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
        return new InMemoryRDFMetadataSelector(_metadataMap.get(id));
    }

    @Override
    public <S extends MetadatasSelector> S selector(Class<S> c) throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private Map<String, RDFMetadata> _metadataMap;
}