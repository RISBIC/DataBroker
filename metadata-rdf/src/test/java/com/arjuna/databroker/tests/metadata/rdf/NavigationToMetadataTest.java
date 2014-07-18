/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.rdf.StoreMetadataInventory;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public class NavigationToMetadataTest
{
    @BeforeClass
    public static void setupInventory()
    {
        try
        {
            List<String>              ids              = new LinkedList<String>();
            Map<String, String>       contentMap       = new HashMap<String, String>();
            Map<String, String>       descriptionIdMap = new HashMap<String, String>();
            Map<String, String>       parentIdMap      = new HashMap<String, String>();
            Map<String, List<String>> childrenIdsMap   = new HashMap<String, List<String>>();

            String test0001 = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("Test0001.rdf"));

            ids.add("id1");
            contentMap.put("id1", test0001);

            DummyMetadataContentStore dummyMetadataContentStore = new DummyMetadataContentStore(ids, contentMap, descriptionIdMap, parentIdMap, childrenIdsMap);
            _metadataInventory = new StoreMetadataInventory(dummyMetadataContentStore);
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
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
