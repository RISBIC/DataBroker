/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataContextSelector;

public interface MutableMetadataContext
{
    public <T> void addMetadataStatement(String name, String type, T value);
    public void     removeMetadataStatement(MetadataStatement<?> metadataStatement);

    public void save();

    public MutableMetadataContextSelector self();
    public MetadataStatementsSelector     metadataStatements();

    public <V> V getView(Class<V> viewInterface)
        throws IllegalArgumentException;
}