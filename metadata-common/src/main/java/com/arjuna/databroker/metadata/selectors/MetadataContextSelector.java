/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MetadataContext;

public interface MetadataContextSelector
{
    public <T extends MetadataContextSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public MetadataContext getMetadataContext();
}