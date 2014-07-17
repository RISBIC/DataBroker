/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.HashMap;
import java.util.Map;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MutableMetadataInventory;

public class MemoryMutableMetadataInventory extends MemoryMetadataInventory implements MutableMetadataInventory
{
    public MemoryMutableMetadataInventory(Map<String, RDFMetadata> metadataMap)
    {
        super(metadataMap);
    }

    @Override
    public Metadata createBlankRootMetadata(String id, Metadata description)
    {
        RDFMetadata newRDFMetadata = new RDFMetadata(id, null, (RDFMetadata) description);
        
        _metadataMap.put(id, newRDFMetadata);

        return newRDFMetadata;
    }

    public Metadata createRootMetadata(String id, RDFMetadata description, String metadataBlob)
    {
        RDFMetadata newRDFMetadata = new RDFMutableMetadata(id, null, description, new HashMap<String, RDFMetadata>(), metadataBlob);
        
        _metadataMap.put(id, newRDFMetadata);

        return newRDFMetadata;
    }

    @Override
    public boolean removeRootMetadata(String id)
    {
        return _metadataMap.remove(id) != null;
    }
}