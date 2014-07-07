/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MutableMetadataContentSelector;

public interface MutableMetadataContent
{
    public <T> MetadataStatement<T> getStatement(String name, String type);
    public <T> void                 addMetadataStatement(String name, String type, T value);
    public void                     removeMetadataStatement(MetadataStatement<?> metadataStatement);

    public void save();

    public MutableMetadataContentSelector self();

    public <V> V getView(Class<V> viewInterface)
        throws IllegalArgumentException;
}