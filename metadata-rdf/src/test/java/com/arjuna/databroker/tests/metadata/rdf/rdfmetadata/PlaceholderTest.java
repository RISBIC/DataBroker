/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf.rdfmetadata;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.rdf.InMemoryRDFMetadataInventory;
import com.arjuna.databroker.metadata.rdf.RDFMetadataInventory;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public class PlaceholderTest
{
    @BeforeClass
    public static void setupInventory()
    {
        _rdfMetadataInventory = new InMemoryRDFMetadataInventory();

//        _rdfMetadataInventory.createRDFRootMetadata("id", "");
    }

    @Test
    public void placeholder01()
    {
        RDFMetadataInventory rdfMetadataInventory = new InMemoryRDFMetadataInventory();

//        MetadataSelector metadataSelector = rdfMetadataInventory.self().metadata("id");
        MetadataSelector metadataSelector = null;
        Metadata         metadata         = metadataSelector.getMetadata();
        Metadata         foundMetadata    = metadataSelector.getMetadata();

        assertSame("Not same metadata objects", metadata, foundMetadata);
    }

    @Test
    public void placeholder02()
    {
        RDFMetadataInventory rdfMetadataInventory = new InMemoryRDFMetadataInventory();

//        MetadataSelector         metadataSelector         = rdfMetadataInventory.self().metadata("id");
        MetadataSelector         metadataSelector         = null;
        MetadataContentsSelector metadataContentsSelector = metadataSelector.contents();
        MetadataContentSelector  metadataContentSelector  = metadataContentsSelector.selector(RDFMetadataContentsSelector.class).withPath("");
    }

    private static RDFMetadataInventory _rdfMetadataInventory;
}
