/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import com.arjuna.databroker.metadata.MutableMetadataInventory;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.rdf.selectors.InMemoryRDFMetadatasSelector;

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
    public MutableMetadataInventory clone()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends MetadatasSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private Map<String, RDFMetadata> _metadataMap;
}