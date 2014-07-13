/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.rdf.InMemoryBlobMetadataInventory;
import com.arjuna.databroker.metadata.rdf.InMemoryBlobMutableMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;

public class MetaContentToViewTest
{
    @BeforeClass
    public static void setupInventory()
    {
        try
        {
            InMemoryBlobMetadataInventory        metadataInventory                    = new InMemoryBlobMetadataInventory();
            InMemoryBlobMutableMetadataInventory inMemoryBlobMutableMetadataInventory = metadataInventory.mutableClone(InMemoryBlobMutableMetadataInventory.class);

            String test0001 = Utils.loadInputStream(MetaContentToViewTest.class.getResourceAsStream("Test0001.rdf"));
            inMemoryBlobMutableMetadataInventory.createRootMetadata("id", test0001, null);

            Metadata metadata = metadataInventory.metadata("id").getMetadata();

            _metadataContent = metadata.contents().selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/test0001#Test01").getMetadataContent();
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
    }

    @Test
    public void metadataContentToView()
    {
        assertNotNull("Not expecting null Metadata Content object", _metadataContent);

        TestView testView = _metadataContent.getView(TestView.class);
        assertNotNull("Not expecting null Test View object", testView);

        String prop01 = testView.getProp01();
        assertEquals("Unexpecting prop01 value", "Value 01", prop01);
    }

    private static MetadataContent _metadataContent;
}
