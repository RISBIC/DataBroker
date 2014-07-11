/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.rdf.InMemoryBlobMetadataInventory;
import com.arjuna.databroker.metadata.rdf.InMemoryBlobMutableMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;

public class NavigationToMetadataContentTest
{
    @BeforeClass
    public static void setupInventory()
    {
        try
        {
            InMemoryBlobMetadataInventory        metadataInventory                    = new InMemoryBlobMetadataInventory();
            InMemoryBlobMutableMetadataInventory inMemoryBlobMutableMetadataInventory = metadataInventory.mutableClone(InMemoryBlobMutableMetadataInventory.class);

            String test0001 = Utils.loadInputStream(NavigationToMetadataContentTest.class.getResourceAsStream("Test0001.rdf"));
            inMemoryBlobMutableMetadataInventory.createRootMetadata("id", test0001, null);

            _metadata = metadataInventory.metadata("id").getMetadata();
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
    }

    @Test
    public void metadataToMetadataContent()
    {
        assertNotNull("Not expecting null RDF Metadata object", _metadata);

        MetadataContentsSelector metadataContentsSelector = _metadata.contents();
        assertNotNull("Not expecting null Metadata Contents Selector object", metadataContentsSelector);

        MetadataContentSelector metadataContentSelector = metadataContentsSelector.selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/test0001#Test01");
        assertNotNull("Not expecting null RDF Path Metadata Content Selector object", metadataContentSelector);
    }

    private static Metadata _metadata;
}
