/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import java.util.Collection;

/**
 * MetadataInventory is an interface through which a metadata inventory can be accessed.
 */
public interface MetadataInventory<T extends Metadata>
{
    /**
     * Returns the ids of the metadata within the metadata inventory.
     *
     * @return the ids of the metadata within the metadata inventory
     */
    public Collection<String> getMetadataIDs();

    /**
     * Returns the metadata, if it is within the metadata inventory.
     * 
     * @param the id of the desired metadata 
     * @return the metadata
     */
    public Metadata getMetadata(String id);

    /**
     * Adds a metadata to the metadata inventory.
     * 
     * @param the id of the metadata to be added to the inventory
     * @param metadata the metadata to be added to the inventory
     */
    public void addMetadata(String id, T metadata);

    /**
     * Removes metadata with specified id from the metadata inventory.
     * 
     * @param the id of the metadata to be removed from the inventory
     * @return indicates if the metadata was removed from the metadata inventory
     */
    public boolean removeMetadata(String id);
}
