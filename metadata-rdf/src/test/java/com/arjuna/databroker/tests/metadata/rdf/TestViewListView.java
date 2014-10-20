/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.List;
import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.annotations.MetadataStatementMapping;

@MetadataContentView
public interface TestViewListView
{
    @MetadataStatementMapping(name="http://rdfs.arjuna.com/metadata/test#propviewlist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<TestView> getPropList01();

    @MetadataStatementMapping(name="http://rdfs.arjuna.com/metadata/test#propviewlist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<TestView> getPropList02();
}