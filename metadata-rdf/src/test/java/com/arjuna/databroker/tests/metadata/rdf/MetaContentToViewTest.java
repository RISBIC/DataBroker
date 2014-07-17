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

public class MetaContentToViewTest
{
    @BeforeClass
    public static void setupInventory()
    {
        try
        {
            MemoryMetadataInventory        metadataInventory              = new MemoryMetadataInventory();
            MemoryMutableMetadataInventory memoryMutableMetadataInventory = metadataInventory.mutableClone(MemoryMutableMetadataInventory.class);

            String test0001 = Utils.loadInputStream(MetaContentToViewTest.class.getResourceAsStream("Test0001.rdf"));
            memoryMutableMetadataInventory.createRootMetadata("id", null, test0001);

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

        String prop01Value = testView.getProp01();
        assertEquals("Unexpecting prop01 value", "Value 01", prop01Value);

        String prop02Value = testView.getProp02();
        assertEquals("Unexpecting prop02 value", "Value 02", prop02Value);

        String prop03Value = testView.getProp03();
        assertEquals("Unexpecting prop03 value", "Value 03", prop03Value);

        String prop04Value = testView.getProp04();
        assertEquals("Unexpecting prop04 value", "Value 04", prop04Value);
    }

    private static MetadataContent _metadataContent;
}
