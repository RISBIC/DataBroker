/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.Metadata;

public interface MetadataSelector
{
    public MetadataSelector         parent();
    public MetadataSelector         description();
    public MetadatasSelector        children();
    public MetadataContentsSelector contents();

    public <T extends MetadataSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public Metadata getMetadata();
}