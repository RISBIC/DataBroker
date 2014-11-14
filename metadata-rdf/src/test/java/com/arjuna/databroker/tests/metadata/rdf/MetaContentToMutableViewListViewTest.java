/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataInventory;
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.rdf.RDFMutableMetadataContent;
import com.arjuna.databroker.metadata.rdf.StoreMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;

public class MetaContentToMutableViewListViewTest
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

            String testList0002 = Utils.loadInputStream(MetaContentToMutableViewListViewTest.class.getResourceAsStream("TestList0002.rdf"));

            ids.add("id1");
            contentMap.put("id1", testList0002);

            DummyMetadataContentStore dummyMetadataContentStore = new DummyMetadataContentStore(ids, contentMap, descriptionIdMap, parentIdMap, childrenIdsMap);
            MetadataInventory         metadataInventory         = new StoreMetadataInventory(dummyMetadataContentStore);
            Metadata                  metadata                  = metadataInventory.metadata("id1").getMetadata();

            _mutableMetadataContent = metadata.contents().selector(RDFMetadataContentsSelector.class).withPath("http://rdf.arjuna.com/testlist0002#TestList01").getMetadataContent().mutableClone(RDFMutableMetadataContent.class);
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
    }

    @Test
    @Ignore
    public void metadataContentToViewListView()
    {
        assertNotNull("Not expecting null Metadata Content object", _mutableMetadataContent);

        MutableTestViewListView mutableTestViewListView = _mutableMetadataContent.getView(MutableTestViewListView.class);
        assertNotNull("Not expecting null Test List View object", mutableTestViewListView);

        List<MutableTestView> propViewList01Value = mutableTestViewListView.getPropViewList01();
        assertNotNull("Not expecting null value for propViewList01Value", propViewList01Value);
        assertEquals("Unexpecting propList01 length", 1, propViewList01Value.size());
        assertNotNull("Not expecting null value for propViewList01Value[0]", propViewList01Value.get(0));
        assertEquals("Unexpect value for propViewList01Value[0]prop01", "Value 01-01", propViewList01Value.get(0).getProp01());
        assertEquals("Unexpect value for propViewList01Value[0]prop02", "Value 02-01", propViewList01Value.get(0).getProp02());
        assertEquals("Unexpect value for propViewList01Value[0]prop03", "Value 03-01", propViewList01Value.get(0).getProp03());
        assertEquals("Unexpect value for propViewList01Value[0]prop04", "Value 04-01", propViewList01Value.get(0).getProp04());

        List<MutableTestView> propViewList02Value = mutableTestViewListView.getPropViewList02();
        assertNotNull("Not expecting null value for propViewList02Value", propViewList02Value);
        assertEquals("Unexpecting propViewList02Value value", 2, propViewList02Value.size());
        assertNotNull("Not expecting null value for propViewList02Value[0]", propViewList02Value.get(0));
        assertNotNull("Not expecting null value for propViewList02Value[1]", propViewList02Value.get(1));
        assertEquals("Unexpect value for propViewList02Value[0]prop01", "Value 01-02", propViewList02Value.get(0).getProp01());
        assertEquals("Unexpect value for propViewList02Value[0]prop02", "Value 02-02", propViewList02Value.get(0).getProp02());
        assertEquals("Unexpect value for propViewList02Value[0]prop03", "Value 03-02", propViewList02Value.get(0).getProp03());
        assertEquals("Unexpect value for propViewList02Value[0]prop04", "Value 04-02", propViewList02Value.get(0).getProp04());
        assertEquals("Unexpect value for propViewList02Value[1]prop01", "Value 01-03", propViewList02Value.get(1).getProp01());
        assertEquals("Unexpect value for propViewList02Value[1]prop02", "Value 02-03", propViewList02Value.get(1).getProp02());
        assertEquals("Unexpect value for propViewList02Value[1]prop03", "Value 03-03", propViewList02Value.get(1).getProp03());
        assertEquals("Unexpect value for propViewList02Value[1]prop04", "Value 04-03", propViewList02Value.get(1).getProp04());

        List<MutableTestView> newPropViewList01 = new LinkedList<MutableTestView>();
        mutableTestViewListView.setPropViewList01(newPropViewList01);
        List<MutableTestView> newPropViewList02 = new LinkedList<MutableTestView>();
        mutableTestViewListView.setPropViewList02(newPropViewList02);

        List<MutableTestView> newPropViewList01Value = mutableTestViewListView.getPropViewList01();
        assertNotNull("Not expecting null value for newPropViewList01Value", newPropViewList01Value);
        assertEquals("Unexpecting propList01 length", 1, newPropViewList01Value.size());
        assertNotNull("Not expecting null value for newPropViewList01Value[0]", newPropViewList01Value.get(0));
        assertEquals("Unexpect value for newPropViewList01Value[0]prop01", "Value 01-01", newPropViewList01Value.get(0).getProp01());
        assertEquals("Unexpect value for newPropViewList01Value[0]prop02", "Value 02-01", newPropViewList01Value.get(0).getProp02());
        assertEquals("Unexpect value for newPropViewList01Value[0]prop03", "Value 03-01", newPropViewList01Value.get(0).getProp03());
        assertEquals("Unexpect value for newPropViewList01Value[0]prop04", "Value 04-01", newPropViewList01Value.get(0).getProp04());

        List<MutableTestView> newPropViewList02Value = mutableTestViewListView.getPropViewList02();
        assertNotNull("Not expecting null value for newPropViewList02Value", newPropViewList02Value);
        assertEquals("Unexpecting newPropViewList02Value value", 2, newPropViewList02Value.size());
        assertNotNull("Not expecting null value for newPropViewList02Value[0]", newPropViewList02Value.get(0));
        assertNotNull("Not expecting null value for newPropViewList02Value[1]", newPropViewList02Value.get(1));
        assertEquals("Unexpect value for newPropViewList02Value[0]prop01", "Value 01-02", newPropViewList02Value.get(0).getProp01());
        assertEquals("Unexpect value for newPropViewList02Value[0]prop02", "Value 02-02", newPropViewList02Value.get(0).getProp02());
        assertEquals("Unexpect value for newPropViewList02Value[0]prop03", "Value 03-02", newPropViewList02Value.get(0).getProp03());
        assertEquals("Unexpect value for newPropViewList02Value[0]prop04", "Value 04-02", newPropViewList02Value.get(0).getProp04());
        assertEquals("Unexpect value for newPropViewList02Value[1]prop01", "Value 01-03", newPropViewList02Value.get(1).getProp01());
        assertEquals("Unexpect value for newPropViewList02Value[1]prop02", "Value 02-03", newPropViewList02Value.get(1).getProp02());
        assertEquals("Unexpect value for newPropViewList02Value[1]prop03", "Value 03-03", newPropViewList02Value.get(1).getProp03());
        assertEquals("Unexpect value for newPropViewList02Value[1]prop04", "Value 04-03", newPropViewList02Value.get(1).getProp04());
    }

    private static MutableMetadataContent _mutableMetadataContent;
}
