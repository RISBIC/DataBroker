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

public class MetaContentToListViewTest
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

            String testList0001 = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("TestList0001.rdf"));

            ids.add("id1");
            contentMap.put("id1", testList0001);

            DummyMetadataContentStore dummyMetadataContentStore = new DummyMetadataContentStore(ids, contentMap, descriptionIdMap, parentIdMap, childrenIdsMap);
            MetadataInventory         metadataInventory         = new StoreMetadataInventory(dummyMetadataContentStore);
            Metadata                  metadata                  = metadataInventory.metadata("id1").getMetadata();

            _metadataContent = metadata.contents().selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/testlist0001#TestList01").getMetadataContent();
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

        TestListView testListView = _metadataContent.getView(TestListView.class);
        assertNotNull("Not expecting null Test View object", testListView);

        List<String> propList01Value = testListView.getPropList01();
        assertNotNull("Not expecting null value for propList01", propList01Value);
        assertEquals("Unexpecting propList01 length", 1, propList01Value.size());

        List<String> propList02Value = testListView.getPropList02();
        assertNotNull("Not expecting null value for propList02", propList02Value);
        assertEquals("Unexpecting propList02 value", 2, propList02Value.size());
    }

    private static MetadataContent _metadataContent;
}
