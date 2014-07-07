/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MutableMetadataContent;

public interface MutableMetadataContentSelector
{
    public <T extends MutableMetadataContentSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public MutableMetadataContent getMutableMetadataContent();
}