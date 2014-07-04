/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;

public interface MetadataStatement<T>
{
    String getName();
    String getType();
    T      getValue();

    public MetadataStatementSelector  self();
    public MetadataStatementsSelector metaFeatures();
}