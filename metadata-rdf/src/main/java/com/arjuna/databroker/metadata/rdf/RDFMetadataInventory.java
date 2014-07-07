/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataInventory;

public class RDFMetadataInventory implements MetadataInventory<RDFMetadata>
{
    public RDFMetadataInventory()
    {
        _rdfMetadataMap = new HashMap<String, RDFMetadata>();
    }
    
    @Override
    public Collection<String> getMetadataIDs()
    {
        return _rdfMetadataMap.keySet();
    }

    @Override
    public Metadata getMetadata(String id)
    {
        return _rdfMetadataMap.get(id);
    }

    @Override
    public void addMetadata(String id, RDFMetadata rdfMetadata)
    {
        _rdfMetadataMap.put(id, rdfMetadata);
    }

    @Override
    public boolean removeMetadata(String id)
    {
        return _rdfMetadataMap.remove(id) != null;
    }
    
    private Map<String, RDFMetadata> _rdfMetadataMap;
}