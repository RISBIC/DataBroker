/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import java.util.List;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class DatabaseMetadatasSelector implements MetadatasSelector
{
    public DatabaseMetadatasSelector(List<String> ids)
    {
        _ids = ids;
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        if ((_ids != null) && _ids.contains(id))
            return new DatabaseMetadataSelector(id);
        else
            return new DatabaseMetadataSelector(null);
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
    
    private List<String> _ids;
}