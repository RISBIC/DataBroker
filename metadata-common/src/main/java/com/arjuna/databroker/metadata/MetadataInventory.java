/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import java.util.Collection;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

/**
 * MetadataInventory is an interface through which a metadata inventory can be accessed.
 */
public interface MetadataInventory<T extends Metadata>
{
    public Collection<String> getMetadataIds();

    public T createBlankRootMetadata(String id);

    public boolean removeRootMetadata(String id);

    public MetadatasSelector self();
}
