/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

public interface MutableMetadataContentsSelector extends MetadataContentsSelector
{
    public <S extends MutableMetadataContentsSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
}