/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.List;
import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.annotations.MetadataStatementMapping;

@MetadataContentView
public interface TestListView
{
    @MetadataStatementMapping(name="http://rdfs.arjuna.com/testlist0001#proplist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<String> getPropList01();

    @MetadataStatementMapping(name="http://rdfs.arjuna.com/testlist0001#proplist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<String> getPropList02();
}