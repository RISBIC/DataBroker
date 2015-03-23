/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import java.util.List;
import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class StoreMetadatasSelector implements MetadatasSelector
{
    public StoreMetadatasSelector(MetadataContentStore metadataContentStore, List<String> ids)
    {
        _metadataContentStore = metadataContentStore;
        _ids                  = ids;
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        if ((_ids != null) && _ids.contains(id))
            return new StoreMetadataSelector(_metadataContentStore, id);
        else
            return new StoreMetadataSelector(_metadataContentStore, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadatasSelector> S selector(Class<S> c) throws IllegalArgumentException
    {
        if (c.isAssignableFrom(StoreMetadatasSelector.class))
            return (S) this;
        else
            return null;
    }

    private MetadataContentStore _metadataContentStore;
    private List<String>         _ids;
}