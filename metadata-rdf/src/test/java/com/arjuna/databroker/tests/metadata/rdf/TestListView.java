/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.List;
import com.arjuna.databroker.metadata.annotations.MetadataView;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;

@MetadataView
public interface TestListView
{
    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#proplist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<String> getPropList01();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#proplist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<String> getPropList02();
}
