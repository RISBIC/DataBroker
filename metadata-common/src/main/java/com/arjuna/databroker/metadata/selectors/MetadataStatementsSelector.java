/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import java.util.Collection;
import com.arjuna.databroker.metadata.MetadataStatement;

public interface MetadataStatementsSelector
{
    public <T extends MetadataStatementsSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public Collection<MetadataStatement<?>> getStatements();
}