/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

/**
 * MetadataInventory is an interface through which a metadata inventory can be accessed.
 */
public interface MetadataInventory
{
    public MetadatasSelector metadatas();
    public MetadataSelector  metadata(String id);

    public <M extends MutableMetadataInventory> M mutableClone(Class<M> c);

    public <S extends MetadatasSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}