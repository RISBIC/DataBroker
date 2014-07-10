/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;

public interface Metadata
{
    public String getId();

    public MetadataContentsSelector contents();
    
    public <M extends MutableMetadata> M mutableClone(Class<M> c);

    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}