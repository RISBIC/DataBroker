/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

public interface MutableMetadatasSelector extends MetadatasSelector
{
    public MutableMetadataSelector mutableMetadata(String id);

    public <S extends MutableMetadatasSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
}