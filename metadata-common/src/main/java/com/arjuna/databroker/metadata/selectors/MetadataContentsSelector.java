/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

public interface MetadataContentsSelector
{
    public <S extends MetadataContentsSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}