/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

public interface MutableMetadataStatement extends MetadataStatement
{
    public void rename(String name);
    public <T> void update(T value);
    public <T> void update(String type, T value);
    
    public void setValue(Object value);
}