/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MetadataContent;

public interface MetadataContentSelector
{
    public MetadataContent getMetadataContent();

    public <S extends MetadataContentSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}