/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import java.util.HashMap;
import javax.ejb.EJB;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.store.MetadataUtils;
import com.arjuna.databroker.metadata.rdf.RDFMetadata;
import com.arjuna.databroker.metadata.rdf.RDFMutableMetadata;

public class DatabaseMetadataSelector implements MetadataSelector
{
    public DatabaseMetadataSelector(String id)
    {
        _id       = id;
        _metadata = null;
    }

    @Override
    public Metadata getMetadata()
    {
        if ((_id != null) && (_metadata == null))
            findMetadata();

        return _metadata;
    }

    @Override
    public MetadataSelector parent()
    {
        if ((_id != null) && (_metadata == null))
            findMetadata();

        return _metadata.parent();
    }

    @Override
    public MetadatasSelector children()
    {
        if ((_id != null) && (_metadata == null))
            findMetadata();

        return _metadata.children();
    }

    @Override
    public MetadataSelector description()
    {
        if ((_id != null) && (_metadata == null))
            findMetadata();

        return _metadata.description();
    }

    @Override
    public MetadataContentsSelector contents()
    {
        if ((_id != null) && (_metadata == null))
            findMetadata();

        return _metadata.contents();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(DatabaseMetadataSelector.class))
            return (S) this;
        else
            return null;
    }

    protected void findMetadata()
    {
        String content = _metadataUtils.getContent(_id);

        _metadata = new RDFMutableMetadata(_id, null, null, new HashMap<String, RDFMetadata>(), content);
    }

    protected String      _id;
    protected RDFMetadata _metadata;

    @EJB
    protected MetadataUtils _metadataUtils;
}