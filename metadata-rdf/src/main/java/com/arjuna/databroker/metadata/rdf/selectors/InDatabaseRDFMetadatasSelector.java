/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import java.util.Collection;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.store.MetadataUtils;

public class InDatabaseRDFMetadatasSelector implements MetadatasSelector
{
    public InDatabaseRDFMetadatasSelector(MetadataUtils metadataUtils)
    {    
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
}