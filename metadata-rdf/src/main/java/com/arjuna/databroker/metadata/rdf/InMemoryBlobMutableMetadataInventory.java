/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Map;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MutableMetadataInventory;

public class InMemoryBlobMutableMetadataInventory extends InMemoryBlobMetadataInventory implements MutableMetadataInventory
{
    public InMemoryBlobMutableMetadataInventory(Map<String, RDFMetadata> metadataMap)
    {
        super(metadataMap);
    }

    public Metadata createBlankRootMetadata(String id, Metadata description)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Metadata createRootMetadata(String id, String metadataBlob, RDFMetadata description)
    {
        RDFMetadata newRDFMetadata = new RDFMutableMetadata(id, null, description, metadataBlob);
        
        _metadataMap.put(id, newRDFMetadata);

        return newRDFMetadata;
    }

    public boolean removeRootMetadata(String id)
    {
        return _metadataMap.remove(id) != null;
    }
}