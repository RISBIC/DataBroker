/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

/**
 * MutableMetadataInventory is an interface through which a changeable metadata inventory can be accessed.
 */
public interface MutableMetadataInventory<T extends Metadata> extends MetadataInventory<T>
{
    public T       createBlankRootMetadata(String id, Metadata description);
    public boolean removeRootMetadata(String id);
}
