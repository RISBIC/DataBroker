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
import com.arjuna.databroker.metadata.rdf.RDFMetadata;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public class DescriptionSearchTest
{
    @BeforeClass
    public static void setupInventory()
    {
        try
        {
            MemoryMetadataInventory        metadataInventory              = new MemoryMetadataInventory();
            MemoryMutableMetadataInventory memoryMutableMetadataInventory = metadataInventory.mutableClone(MemoryMutableMetadataInventory.class);

            String   test0002         = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("Test0002.rdf"));
            Metadata metadataTest0002 = memoryMutableMetadataInventory.createRootMetadata("id2", null, test0002);
            String   test0001         = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("Test0001.rdf"));
            Metadata metadataTest0001 = memoryMutableMetadataInventory.createRootMetadata("id1", (RDFMetadata) metadataTest0002, test0001);

            _metadata = metadataTest0001;
        }
        catch (Throwable throwable)
        {
            fail("Failed to populate Metadata Inventory");
        }
     }

    @Test
    public void obtainDescriptionMetadata()
    {
        assertNotNull("Not expecting null RDF Metadata object", _metadata);

        MetadataSelector descriptionMetadataSelector = _metadata.description();
        assertNotNull("Not expecting null Description Metadata Selector object", descriptionMetadataSelector);

        Metadata descriptionMetadata = descriptionMetadataSelector.getMetadata();
        assertNotNull("Not expecting null Description Metadata object", descriptionMetadata);
    }

    @Test
    public void obtainViaKeyword()
    {
        assertNotNull("Not expecting null RDF Metadata object", _metadata);

        MetadataSelector descriptionMetadataSelector = _metadata.description();
        assertNotNull("Not expecting null Description Metadata Selector object", descriptionMetadataSelector);

        MetadataContentsSelector descriptionMetadataContentsSelector = descriptionMetadataSelector.contents();
        assertNotNull("Not expecting null Description Metadata Contents Selector object", descriptionMetadataContentsSelector);

        RDFMetadataContentsSelector rdfDescriptionMetadataContentsSelector = descriptionMetadataContentsSelector.selector(RDFMetadataContentsSelector.class);
        assertNotNull("Not expecting null RDF Metadata Contents Selector object", rdfDescriptionMetadataContentsSelector);

        MetadataContentSelector keywordMetadataContentSelector = rdfDescriptionMetadataContentsSelector.withPropertyValue("http://rdfs.arjuna.com/test0002#keyword", "Keyword01");
        assertNotNull("Not expecting null Keyword Metadata Contents Selector object", keywordMetadataContentSelector);

        MetadataContent keywordMetadataContent = keywordMetadataContentSelector.getMetadataContent();
        assertNotNull("Not expecting null Keyword Metadata Content Selector object", keywordMetadataContent);

        InfoView infoView = keywordMetadataContent.getView(InfoView.class);
        assertNotNull("Not expecting null info view object", infoView);

        String keyword = infoView.getKeyword();
        assertEquals("Unexpected subject value", "Keyword01", keyword);

        String subject = infoView.getSubject();
        assertEquals("Unexpected subject value", "http://rdf.arjuna.com/test0001#Test01", subject);

        MetadataContentsSelector metadataContentsSelector = _metadata.contents();
        assertNotNull("Not expecting null Metadata Contents Selector object", metadataContentsSelector);

        RDFMetadataContentsSelector rdfMetadataContentsSelector = metadataContentsSelector.selector(RDFMetadataContentsSelector.class);
        assertNotNull("Not expecting null Metadata Contents Selector object", rdfMetadataContentsSelector);

        RDFMetadataContentSelector rdfMetadataContentSelector = rdfMetadataContentsSelector.withPath(subject);
        assertNotNull("Not expecting null Metadata Content Selector object", rdfMetadataContentsSelector);

        MetadataContent metadataContent = rdfMetadataContentSelector.getMetadataContent();
        assertNotNull("Not expecting null Metadata Content object", metadataContent);

        TestView testView = metadataContent.getView(TestView.class);
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

    private static Metadata _metadata;
}
