/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.Metadata;

public interface MetadataSelector
{
    public Metadata getMetadata();

    public MetadataSelector         parent();
    public MetadatasSelector        children();
    public MetadataSelector         description();
    public MetadataContentsSelector contents();

    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}