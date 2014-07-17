/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Map;
import java.util.HashMap;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.MutableMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.MemoryMetadatasSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class MemoryMetadataInventory implements MetadataInventory
{
    public MemoryMetadataInventory()
    {
        _metadataMap = new HashMap<String, RDFMetadata>();
    }

    public MemoryMetadataInventory(Map<String, RDFMetadata> metadataMap)
    {
        _metadataMap = metadataMap;
    }

    @Override
    public MetadatasSelector metadatas()
    {
        return new MemoryMetadatasSelector(_metadataMap);
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        return new RDFMetadataSelector(_metadataMap.get(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadataInventory> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(MemoryMutableMetadataInventory.class))
            return (M) new MemoryMutableMetadataInventory(_metadataMap);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadatasSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(MemoryMetadatasSelector.class))
            return (S) new MemoryMetadatasSelector(_metadataMap);
        else
            return null;
    }

    protected Map<String, RDFMetadata> _metadataMap;
}