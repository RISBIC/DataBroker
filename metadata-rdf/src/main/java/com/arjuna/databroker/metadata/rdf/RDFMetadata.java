/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.util.Map;
import java.util.HashMap;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MutableMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadatasSelector;
import com.hp.hpl.jena.rdf.model.Model;

public class RDFMetadata implements Metadata
{
    public RDFMetadata(String id, RDFMetadata parent, RDFMetadata description)
    {
        this(id, parent, description, new HashMap<String, RDFMetadata>(), null);
    }

    public RDFMetadata(String id, RDFMetadata parent, RDFMetadata description, Map<String, RDFMetadata> children, Model model)
    {
        _id          = id;
        _parent      = parent;
        _children    = children;
        _description = description;
        _model       = model;
    }

    @Override
    public String getId()
    {
        return _id;
    }

    @Override
    public MetadataSelector parent()
    {
        return new RDFMetadataSelector(_parent);
    }

    @Override
    public MetadatasSelector children()
    {
        return new RDFMetadatasSelector(_children);
    }

    @Override
    public MetadataSelector description()
    {
        return new RDFMetadataSelector(_description);
    }

    @Override
    public MetadataContentsSelector contents()
    {
        return new RDFMetadataContentsSelector(_model);
    }

    public String getRawRDF()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadata> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(RDFMutableMetadata.class))
            return (M) new RDFMutableMetadata(_id, _parent, _description, _children, _model);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataSelector.class))
            return (S) new RDFMetadataSelector(this);
        else
            return null;
    }

    protected String                   _id;
    protected RDFMetadata              _parent;
    protected Map<String, RDFMetadata> _children;
    protected RDFMetadata              _description;
    protected Model                    _model;
}