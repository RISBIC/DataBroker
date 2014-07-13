/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MutableMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.hp.hpl.jena.rdf.model.Model;

public class RDFMetadata implements Metadata
{
    private static final Logger logger = Logger.getLogger(RDFMetadata.class.getName());

    public RDFMetadata(String id, RDFMetadata parent, RDFMetadata description)
    {
        _id          = id;
        _parent      = parent;
        _children    = new HashMap<String, RDFMetadata>();
        _description = description;
        _model       = null;
    }

    @Override
    public String getId()
    {
        return _id;
    }

    public String getRawRDF()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <M extends MutableMetadata> M mutableClone(Class<M> c)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadataContentsSelector contents()
    {
        return new RDFMetadataContentsSelector(_model);
    }

    @Override
    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    protected String                   _id;
    protected RDFMetadata              _parent;
    protected Map<String, RDFMetadata> _children;
    protected RDFMetadata              _description;
    protected Model                    _model;
}