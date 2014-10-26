/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import com.arjuna.databroker.metadata.annotations.MetadataView;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;

@MetadataView
public interface InfoView
{
    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#subject", type="http://www.w3.org/2001/XMLSchema#string")
    public String getSubject();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#keyword", type="http://www.w3.org/2001/XMLSchema#string")
    public String getKeyword();
}
