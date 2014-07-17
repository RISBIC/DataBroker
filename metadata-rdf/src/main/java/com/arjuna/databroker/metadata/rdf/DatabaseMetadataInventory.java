/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import javax.ejb.EJB;

import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.MutableMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.DatabaseMetadatasSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.store.MetadataUtils;

public class DatabaseMetadataInventory implements MetadataInventory
{
    public DatabaseMetadataInventory()
    {
    }

    @Override
    public MetadatasSelector metadatas()
    {
    	return new DatabaseMetadatasSelector();
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
    public <M extends MutableMetadataInventory> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(DatabaseMutableMetadataInventory.class))
            return (M) new DatabaseMutableMetadataInventory();
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadatasSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(DatabaseMetadatasSelector.class))
            return (S) new DatabaseMetadatasSelector();
        else
            return null;
    }

    @EJB
    protected MetadataUtils _metadataUtils;
}