/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf.rdfmetadata;

import org.junit.Test;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.rdf.RDFMetadataInventory;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public class PlaceholderTest
{
    @Test
    public void placeholder()
    {
        RDFMetadataInventory rdfMetadataInventory = new RDFMetadataInventory();

        String           metadataId              = "id";
        String           metadataContentSelector = "key";
        Metadata         metadata                = rdfMetadataInventory.getMetadata(metadataId);
        MetadataSelector metadataContent         = metadata.self();
    }
}
