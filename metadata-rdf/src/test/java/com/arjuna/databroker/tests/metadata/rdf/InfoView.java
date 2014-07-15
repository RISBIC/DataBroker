/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.annotations.MetadataStatementMapping;

@MetadataContentView
public interface InfoView
{
    @MetadataStatementMapping(name="http://rdfs.arjuna.com/test0002#subject", type="http://www.w3.org/2001/XMLSchema#string")
    public String getSubject();

    @MetadataStatementMapping(name="http://rdfs.arjuna.com/test0002#keyword", type="http://www.w3.org/2001/XMLSchema#string")
    public String getKeyword();
}