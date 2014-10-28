/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.List;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;
import com.arjuna.databroker.metadata.annotations.MutableMetadataView;

@MutableMetadataView
public interface MutableTestViewListView
{
    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#propviewlist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<MutableTestView> getPropViewList01();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#propviewlist01", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public void setPropViewList01(List<MutableTestView> propViewList02);

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#propviewlist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public List<MutableTestView> getPropViewList02();

    @GetMetadataMapping(name="http://rdfs.arjuna.com/metadata/test#propviewlist02", type="http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq")
    public void setPropViewList02(List<MutableTestView> propViewList02);
}
