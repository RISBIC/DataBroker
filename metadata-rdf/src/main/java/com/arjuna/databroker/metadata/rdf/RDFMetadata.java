/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public class RDFMetadata implements Metadata
{
    public RDFMetadata(String id, String rawRDF)
    {
        _id     = id;
        _rawRDF = rawRDF;
    }

    @Override
    public String getId()
    {
        return _id;
    }

    public String getRawRDF()
    {
        return _rawRDF;
    }

    public void setRawRDF(String rawRDF)
    {
        _rawRDF = rawRDF;
    }

    @Override
    public Metadata createBlankChild(String id, Metadata description)
    {
        return new RDFMetadata(id, "");
    }

    @Override
    public MetadataSelector self()
    {
        return new RDFMetadataSelector(this);
    }

    private String _id;
    private String _rawRDF;
}