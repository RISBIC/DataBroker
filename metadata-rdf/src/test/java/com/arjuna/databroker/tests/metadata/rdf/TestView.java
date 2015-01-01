/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import com.arjuna.databroker.metadata.annotations.MetadataView;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;

@MetadataView
public interface TestView
{
    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop01", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp01();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop02", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp02();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop03", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp03();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop04", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp04();
}
