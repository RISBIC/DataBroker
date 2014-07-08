/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Collection;
import javax.ejb.EJB;
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
    
    @Override
    public RDFMetadata createBlankRootMetadata(String id)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    @Override
    public RDFMetadata createRDFRootMetadata(String id, String rawRDF)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeRootMetadata(String id)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadatasSelector self()
    {
        return new InDatabaseRDFMetadatasSelector(_metadataUtils);
    }

    @EJB
    private MetadataUtils _metadataUtils;
}