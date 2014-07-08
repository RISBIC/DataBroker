/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class InMemoryRDFMetadataInventory implements RDFMetadataInventory
{
    public InMemoryRDFMetadataInventory()
    {
        _metadataMap = new HashMap<String, RDFMetadata>();
    }

    @Override
    public Collection<String> getMetadataIds()
    {
        return _metadataMap.keySet();
    }

    @Override
    public RDFMetadata createBlankRootMetadata(String id)
    {
        RDFMetadata rdfMetadata = new RDFMetadata(id, "");

        _metadataMap.put(id, rdfMetadata);

        return rdfMetadata;
    }

    @Override
    public RDFMetadata createRDFRootMetadata(String id, String rawRDF)
    {
        RDFMetadata rdfMetadata = new RDFMetadata(id, rawRDF);

        _metadataMap.put(id, rdfMetadata);

        return rdfMetadata;
    }

    @Override
    public boolean removeRootMetadata(String id)
    {
        return _metadataMap.remove(id) != null;
    }

    @Override
    public MetadatasSelector self()
    {
        return new InMemoryRDFMetadatasSelector(_metadataMap);
    }

    private Map<String, RDFMetadata> _metadataMap;
}