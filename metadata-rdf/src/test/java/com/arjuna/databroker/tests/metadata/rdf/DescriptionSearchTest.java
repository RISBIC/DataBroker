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
            InMemoryBlobMetadataInventory        metadataInventory                    = new InMemoryBlobMetadataInventory();
            InMemoryBlobMutableMetadataInventory inMemoryBlobMutableMetadataInventory = metadataInventory.mutableClone(InMemoryBlobMutableMetadataInventory.class);

            String   test0002         = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("Test0002.rdf"));
            Metadata metadataTest0002 = inMemoryBlobMutableMetadataInventory.createRootMetadata("id2", null, test0002);
            String   test0003         = Utils.loadInputStream(DescriptionSearchTest.class.getResourceAsStream("Test0003.rdf"));
            Metadata metadataTest0003 = inMemoryBlobMutableMetadataInventory.createRootMetadata("id3", (RDFMetadata) metadataTest0002, test0003);

            _metadata = metadataTest0003;
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

        RDFMetadataContentsSelector rfdMetadataContentsSelector = descriptionMetadataContentsSelector.selector(RDFMetadataContentsSelector.class);
        assertNotNull("Not expecting null RDF Metadata Contents Selector object", rfdMetadataContentsSelector);

        MetadataContentSelector keywordMetadataContentSelector = rfdMetadataContentsSelector.withPropertyValue("http://rdfs.arjuna.com/test0002#keyword", "Keyword01");
        assertNotNull("Not expecting null Keyword Metadata Contents Selector object", keywordMetadataContentSelector);

        MetadataContent keywordMetadataContent = keywordMetadataContentSelector.getMetadataContent();
        assertNotNull("Not expecting null Keyword Metadata Content Selector object", keywordMetadataContent);

        InfoView infoView = keywordMetadataContent.getView(InfoView.class);
        assertNotNull("Not expecting null info view object", infoView);

        String subject = infoView.getSubject();
        assertEquals("Unexpected subject value", "http://rdf.arjuna.com/test0003#Test01", subject);
    }

    private static Metadata _metadata;
}
