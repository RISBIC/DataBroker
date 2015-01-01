/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MutableMetadata;

public interface MutableMetadataSelector extends MetadataSelector
{
    public MutableMetadata getMetadataMetadata();

    public MutableMetadataSelector         mutableParent();
    public MutableMetadatasSelector        mutableChildren();
    public MutableMetadataSelector         mutableDescription();
    public MutableMetadataContentsSelector mutableContents();

    public <S extends MutableMetadataSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
}