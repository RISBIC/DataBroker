/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.MutableMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.StoreMetadataSelector;
import com.arjuna.databroker.metadata.rdf.selectors.StoreMetadatasSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class StoreMetadataInventory implements MetadataInventory
{
    public StoreMetadataInventory(MetadataContentStore metadataContentStore)
    {
        _metadataContentStore = metadataContentStore;
    }

    @Override
    public MetadatasSelector metadatas()
    {
        return new StoreMetadatasSelector(_metadataContentStore, _metadataContentStore.getIds());
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        return new StoreMetadataSelector(_metadataContentStore, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadataInventory> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(StoreMutableMetadataInventory.class))
            return (M) new StoreMutableMetadataInventory(_metadataContentStore);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadatasSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(StoreMetadatasSelector.class))
            return (S) new StoreMetadatasSelector(_metadataContentStore, _metadataContentStore.getIds());
        else
            return null;
    }

    protected MetadataContentStore _metadataContentStore;
}