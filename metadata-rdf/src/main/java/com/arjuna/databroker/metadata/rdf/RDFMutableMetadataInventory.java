/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.MetadataInventory;

public interface RDFMutableMetadataInventory extends MetadataInventory<RDFMetadata>
{
    public RDFMetadata createRDFRootMetadata(String id, String rawRDF);
}