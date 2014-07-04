/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import java.util.Collection;
import com.arjuna.databroker.metadata.MetadataContext;

public interface MetadataContextsSelector
{
    public <T extends MetadataContextsSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public Collection<MetadataContext> getMetadataContexts();
}