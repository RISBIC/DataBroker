/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.annotations.MetadataStatementMapping;

@MetadataContentView
public interface TestView
{
    @MetadataStatementMapping(name="http://rdfs.arjuna.com/test0001#prop01", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp01();

    @MetadataStatementMapping(name="http://rdfs.arjuna.com/test0001#prop02", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp02();

    @MetadataStatementMapping(name="http://rdfs.arjuna.com/test0001#prop03", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp03();

    @MetadataStatementMapping(name="http://rdfs.arjuna.com/test0001#prop04", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp04();
}