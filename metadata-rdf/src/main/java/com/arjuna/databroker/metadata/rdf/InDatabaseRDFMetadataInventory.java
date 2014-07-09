/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Collection;
import javax.ejb.EJB;
import com.arjuna.databroker.metadata.MutableMetadataInventory;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.store.MetadataUtils;

public class InDatabaseRDFMetadataInventory implements RDFMetadataInventory
{
    public InDatabaseRDFMetadataInventory()
    {
    }

    @Override
    public Collection<String> getMetadataIds()
    {
        return _metadataUtils.getIds();
    }

    public MutableMetadataInventory<RDFMetadata> clone()
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