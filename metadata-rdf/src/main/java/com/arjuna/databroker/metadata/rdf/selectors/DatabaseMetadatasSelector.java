/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class DatabaseMetadatasSelector implements MetadatasSelector
{
    public DatabaseMetadatasSelector()
    {
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        // TODO
        throw new UnsupportedOperationException();
//        return new RDFMetadataSelector(_metadataMap.get(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadatasSelector> S selector(Class<S> c) throws IllegalArgumentException
    {
        if (c.isAssignableFrom(DatabaseMetadatasSelector.class))
            return (S) this;
        else
            return null;
    }
}