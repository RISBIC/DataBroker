/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.logging.Logger;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.MutableMetadata;

public class StoreMutableMetadata extends StoreMetadata implements MutableMetadata
{
    private static final Logger logger = Logger.getLogger(StoreMutableMetadata.class.getName());

    public StoreMutableMetadata(MetadataContentStore metadataContentStore, String id)
    {
        super(metadataContentStore, id);
    }

    public Metadata createBlankChild(String id, Metadata description)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}