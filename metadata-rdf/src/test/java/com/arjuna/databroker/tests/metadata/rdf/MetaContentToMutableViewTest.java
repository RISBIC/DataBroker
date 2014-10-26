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

public class MetaContentToMutableViewTest
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
            MetadataInventory         metadataInventory         = new StoreMetadataInventory(dummyMetadataContentStore);
            Metadata                  metadata                  = metadataInventory.metadata("id1").getMetadata();

            _mutableMetadataContent = metadata.contents().selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/test0001#Test01").getMetadataContent().mutableClone(RDFMutableMetadataContent.class);
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
    }

    @Test
    public void metadataContentToView()
    {
        assertNotNull("Not expecting null Metadata Content object", _mutableMetadataContent);

        MutableTestView mutableTestView = _mutableMetadataContent.getView(MutableTestView.class);
        assertNotNull("Not expecting null Test View object", mutableTestView);

        String prop01Value = mutableTestView.getProp01();
        assertEquals("Unexpecting prop01 value", "Value 01", prop01Value);

        String prop02Value = mutableTestView.getProp02();
        assertEquals("Unexpecting prop02 value", "Value 02", prop02Value);

        String prop03Value = mutableTestView.getProp03();
        assertEquals("Unexpecting prop03 value", "Value 03", prop03Value);

        String prop04Value = mutableTestView.getProp04();
        assertEquals("Unexpecting prop04 value", "Value 04", prop04Value);

        mutableTestView.setProp01("New Value 01");
        mutableTestView.setProp02("New Value 02");
        mutableTestView.setProp03("New Value 03");
        mutableTestView.setProp04("New Value 04");

        String newProp01Value = mutableTestView.getProp01();
        assertEquals("Unexpecting new prop01 value", "New Value 01", newProp01Value);

        String newProp02Value = mutableTestView.getProp02();
        assertEquals("Unexpecting new prop02 value", "New Value 02", newProp02Value);

        String newProp03Value = mutableTestView.getProp03();
        assertEquals("Unexpecting new prop03 value", "New Value 03", newProp03Value);

        String newProp04Value = mutableTestView.getProp04();
        assertEquals("Unexpecting new prop04 value", "New Value 04", newProp04Value);
    }

    private static MutableMetadataContent _mutableMetadataContent;
}
