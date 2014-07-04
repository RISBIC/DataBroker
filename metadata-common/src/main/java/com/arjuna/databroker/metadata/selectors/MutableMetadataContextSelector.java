/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MutableMetadataContext;

public interface MutableMetadataContextSelector
{
    public <T extends MutableMetadataContextSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public MutableMetadataContext getMutableMetadataContext();
}