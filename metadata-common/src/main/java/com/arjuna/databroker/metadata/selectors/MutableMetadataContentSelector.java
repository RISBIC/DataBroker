/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MutableMetadataContent;

public interface MutableMetadataContentSelector extends MetadataContentSelector
{
    public MutableMetadataContent getMutableMetadataContent();

    public <S extends MutableMetadataContentSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
}