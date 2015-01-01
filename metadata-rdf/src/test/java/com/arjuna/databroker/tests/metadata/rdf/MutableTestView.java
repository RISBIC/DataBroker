/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;
import com.arjuna.databroker.metadata.annotations.SetMetadataMapping;
import com.arjuna.databroker.metadata.annotations.MutableMetadataView;

@MutableMetadataView
public interface MutableTestView
{
    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop01", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp01();

    @SetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop01", type="http://www.w3.org/2001/XMLSchema#string")
    public void setProp01(String prop01);

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop02", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp02();

    @SetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop02", type="http://www.w3.org/2001/XMLSchema#string")
    public void setProp02(String prop02);

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop03", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp03();

    @SetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop03", type="http://www.w3.org/2001/XMLSchema#string")
    public void setProp03(String prop03);

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop04", type="http://www.w3.org/2001/XMLSchema#string")
    public String getProp04();

    @SetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#prop04", type="http://www.w3.org/2001/XMLSchema#string")
    public void setProp04(String prop04);
}
