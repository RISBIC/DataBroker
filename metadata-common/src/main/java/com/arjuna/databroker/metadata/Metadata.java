/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public interface Metadata
{
    public String getId();

    public MutableMetadata clone();

    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}