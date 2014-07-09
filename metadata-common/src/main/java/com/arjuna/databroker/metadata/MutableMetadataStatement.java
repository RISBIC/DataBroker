/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

public interface MutableMetadataStatement<T> extends MetadataStatement<T>
{
    public void rename(String name);
    public void update(T value);
    public void update(String type, T value);
}