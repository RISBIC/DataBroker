/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.MutableMetadataInventory;

public class StoreMutableMetadataInventory extends StoreMetadataInventory implements MutableMetadataInventory
{
    public StoreMutableMetadataInventory(MetadataContentStore metadataContentStore)
    {
        super(metadataContentStore);
    }

    @Override
    public Metadata createBlankRootMetadata(String id, Metadata description)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Metadata createRootMetadata(String id, StoreMetadata description, String metadataBlob)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeRootMetadata(String id)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}