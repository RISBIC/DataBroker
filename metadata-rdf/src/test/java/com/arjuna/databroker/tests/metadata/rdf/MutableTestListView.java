/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.List;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;
import com.arjuna.databroker.metadata.annotations.MutableMetadataView;
import com.arjuna.databroker.metadata.annotations.SetMetadataMapping;

@MutableMetadataView
public interface MutableTestListView
{
    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#proplist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<String> getPropList01();

    @SetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#proplist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public void setPropList01(List<String> propList01);

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#proplist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<String> getPropList02();

    @SetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#proplist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public void setPropList02(List<String> propList02);
}
