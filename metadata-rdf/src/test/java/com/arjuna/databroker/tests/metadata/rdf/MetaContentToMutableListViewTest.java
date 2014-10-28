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
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.rdf.RDFMutableMetadataContent;
import com.arjuna.databroker.metadata.rdf.StoreMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;

public class MetaContentToMutableListViewTest
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

            _mutableMetadataContent = metadata.contents().selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/testlist0001#TestList01").getMetadataContent().mutableClone(RDFMutableMetadataContent.class);
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
    }

    @Test
    public void metadataContentToListView()
    {
        assertNotNull("Not expecting null Metadata Content object", _mutableMetadataContent);

        MutableTestListView mutableTestListView = _mutableMetadataContent.getView(MutableTestListView.class);
        assertNotNull("Not expecting null Test List View object", mutableTestListView);

        List<String> propList01Value = mutableTestListView.getPropList01();
        assertNotNull("Not expecting null value for propList01", propList01Value);
        assertEquals("Unexpecting propList01 length", 1, propList01Value.size());
        assertEquals("Unexpect value for propList01[0]", "Value 01", propList01Value.get(0));

        List<String> propList02Value = mutableTestListView.getPropList02();
        assertNotNull("Not expecting null value for propList02", propList02Value);
        assertEquals("Unexpecting propList02 value", 2, propList02Value.size());
        assertEquals("Unexpect value for propList02[0]", "Value 01", propList02Value.get(0));
        assertEquals("Unexpect value for propList02[1]", "Value 02", propList02Value.get(1));

        List<String> newPropList01 = new LinkedList<>();
        propList01Value.add("New Value 01-01");
        mutableTestListView.setPropList01(newPropList01);

        List<String> newPropList02 = new LinkedList<>();
        propList02Value.add("New Value 02-01");
        propList02Value.add("New Value 02-02");
        mutableTestListView.setPropList02(newPropList02);

        List<String> newPropList01Value = mutableTestListView.getPropList01();
        assertNotNull("Not expecting null value for newPropList01", newPropList01Value);
        assertEquals("Unexpecting newPropList01 length", 1, newPropList01Value.size());
        assertEquals("Unexpect value for newPropList01[0]", "New Value 01-01", newPropList01Value.get(0));

        List<String> newPropList02Value = mutableTestListView.getPropList02();
        assertNotNull("Not expecting null value for newPropList02", newPropList02Value);
        assertEquals("Unexpecting newPropList02 value", 2, newPropList02Value.size());
        assertEquals("Unexpect value for newPropList02[0]", "New Value 02-01", newPropList02Value.get(0));
        assertEquals("Unexpect value for newPropList02[1]", "New Value 02-02", newPropList02Value.get(1));
    }

    private static MutableMetadataContent _mutableMetadataContent;
}
