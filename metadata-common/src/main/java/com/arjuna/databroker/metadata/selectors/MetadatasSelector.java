/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

public interface MetadatasSelector
{
    public MetadataSelector metadata(String id);

    public <S extends MetadatasSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}