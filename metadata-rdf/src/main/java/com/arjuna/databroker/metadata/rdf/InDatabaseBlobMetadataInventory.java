/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import javax.ejb.EJB;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.MutableMetadataInventory;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.store.MetadataUtils;

public class InDatabaseBlobMetadataInventory implements MetadataInventory
{
    public InDatabaseBlobMetadataInventory()
    {
    }

    @Override
    public MetadatasSelector metadatas()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadataSelector metadata(String id)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <M extends MutableMetadataInventory> M mutableClone(Class<M> c)
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

    @EJB
    private MetadataUtils _metadataUtils;
}