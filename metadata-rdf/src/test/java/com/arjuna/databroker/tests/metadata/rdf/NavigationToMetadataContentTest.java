/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.rdf.MemoryMetadataInventory;
import com.arjuna.databroker.metadata.rdf.MemoryMutableMetadataInventory;
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
            MemoryMetadataInventory        metadataInventory              = new MemoryMetadataInventory();
            MemoryMutableMetadataInventory memoryMutableMetadataInventory = metadataInventory.mutableClone(MemoryMutableMetadataInventory.class);

            String test0001 = Utils.loadInputStream(NavigationToMetadataContentTest.class.getResourceAsStream("Test0001.rdf"));
            memoryMutableMetadataInventory.createRootMetadata("id", null, test0001);

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
        assertNotNull("Not expecting null RDF Metadata Content Selector object", metadataContentSelector);
        
        MetadataContent metadataContent = metadataContentSelector.getMetadataContent();
        assertNotNull("Not expecting null Metadata Content object", metadataContent);
    }

    private static Metadata _metadata;
}
