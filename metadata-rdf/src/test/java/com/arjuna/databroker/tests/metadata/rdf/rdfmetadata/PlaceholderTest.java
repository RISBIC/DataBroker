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
import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public class PlaceholderTest
{
    @BeforeClass
    public static void setupInventory()
    {
        _rdfMetadataInventory = new InMemoryRDFMetadataInventory();

        _rdfMetadataInventory.createRDFRootMetadata("id", "");
    }

    @Test
    public void placeholder()
    {
        RDFMetadataInventory rdfMetadataInventory = new InMemoryRDFMetadataInventory();

        MetadataSelector metadataSelector = rdfMetadataInventory.self().metadata("id");
        Metadata         metadata         = metadataSelector.getMetadata();
        Metadata         foundMetadata    = metadataSelector.getMetadata();

        assertSame("Not same metadata objects", metadata, foundMetadata);
    }
    
    private static RDFMetadataInventory _rdfMetadataInventory;
}
