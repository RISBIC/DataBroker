/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;

public interface MetadataContext
{
    public MutableMetadataContext clone();

    public MetadataSelector           self();
    public MetadataStatementsSelector metadataStatements();

    public <V> V getView(Class<V> viewInterface)
        throws IllegalArgumentException;
}