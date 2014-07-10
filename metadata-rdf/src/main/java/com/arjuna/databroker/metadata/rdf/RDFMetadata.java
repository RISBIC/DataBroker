/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.HashMap;
import java.util.Map;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MutableMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;

public class RDFMetadata implements Metadata
{
    public RDFMetadata(String id, String rawRDF, RDFMetadata parent, RDFMetadata description)
    {
        _id          = id;
        _rawRDF      = rawRDF;
        _parent      = parent;
        _children    = new HashMap<String, RDFMetadata>();
        _description = description;
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
    public <M extends MutableMetadata> M mutableClone(Class<M> c)
    {
        return null;
    }

    @Override
    public MetadataContentsSelector contents()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    private String                   _id;
    private String                   _rawRDF;
    private RDFMetadata              _parent;
    private Map<String, RDFMetadata> _children;
    private RDFMetadata              _description;
}