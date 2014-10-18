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
import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.rdf.StoreMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;

public class MetaContentToViewListViewTest
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

            String testList0002 = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("TestList0002.rdf"));

            ids.add("id1");
            contentMap.put("id1", testList0002);

            DummyMetadataContentStore dummyMetadataContentStore = new DummyMetadataContentStore(ids, contentMap, descriptionIdMap, parentIdMap, childrenIdsMap);
            MetadataInventory         metadataInventory         = new StoreMetadataInventory(dummyMetadataContentStore);
            Metadata                  metadata                  = metadataInventory.metadata("id1").getMetadata();

            _metadataContent = metadata.contents().selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/testlist0002#TestList01").getMetadataContent();
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
    }

    @Test
    public void metadataContentToViewListView()
    {
        assertNotNull("Not expecting null Metadata Content object", _metadataContent);

        TestViewListView testViewListView = _metadataContent.getView(TestViewListView.class);
        assertNotNull("Not expecting null Test List View object", testViewListView);

        List<TestView> propList01Value = testViewListView.getPropList01();
        assertNotNull("Not expecting null value for propList01", propList01Value);
        assertEquals("Unexpecting propList01 length", 1, propList01Value.size());
        assertEquals("Unexpect value for propList01[0]", "Value 01", propList01Value.get(0));

        List<TestView> propList02Value = testViewListView.getPropList02();
        assertNotNull("Not expecting null value for propList02", propList02Value);
        assertEquals("Unexpecting propList02 value", 2, propList02Value.size());
        assertEquals("Unexpect value for propList02[0]", "Value 01", propList02Value.get(0));
        assertEquals("Unexpect value for propList02[1]", "Value 02", propList02Value.get(1));
    }

    private static MetadataContent _metadataContent;
}
