/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.rdf.InMemoryBlobMetadataInventory;
import com.arjuna.databroker.metadata.rdf.InMemoryBlobMutableMetadataInventory;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class NavigationToMetadataTest
{
    @BeforeClass
    public static void setupInventory()
    {
        _metadataInventory = new InMemoryBlobMetadataInventory();

        InMemoryBlobMutableMetadataInventory inMemoryBlobMutableMetadataInventory = _metadataInventory.mutableClone(InMemoryBlobMutableMetadataInventory.class);
        inMemoryBlobMutableMetadataInventory.createRootMetadata("id", "RDF text", null);
    }

    @Test
    public void inventoryToMetadata()
    {
        assertNotNull("Not expecting null RDF Metadata Inventory object", _metadataInventory);

        MetadataSelector metadataSelector = _metadataInventory.metadata("id");
        assertNotNull("Not expecting null Metadata Selector object", metadataSelector);

        Metadata metadata = metadataSelector.getMetadata();
        assertNotNull("Not expecting null Metadata object", metadata);
    }

    @Test
    public void inventoryToMetadataIndirect()
    {
        assertNotNull("Not expecting null RDF Metadata Inventory object", _metadataInventory);

        MetadatasSelector metadatasSelector = _metadataInventory.metadatas();
        assertNotNull("Not expecting null Metadatas Selector object", metadatasSelector);

        MetadataSelector metadataSelector = metadatasSelector.metadata("id");
        assertNotNull("Not expecting null Metadata Selector object", metadataSelector);

        Metadata metadata = metadataSelector.getMetadata();
        assertNotNull("Not expecting null Metadata object", metadata);
    }

    private static MetadataInventory _metadataInventory;
}
