/*
5 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import java.util.Collection;
import com.arjuna.databroker.metadata.MetadataContent;

public interface MutableMetadataContentsSelector
{
    public <T extends MutableMetadataContentsSelector> T selector(Class<T> c)
        throws IllegalArgumentException;

    public Collection<MetadataContent> getMetadataContents();
}