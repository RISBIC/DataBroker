/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MutableMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class RDFMetadata implements Metadata
{
    private static final Logger logger = Logger.getLogger(RDFMetadata.class.getName());

    public RDFMetadata(String id, RDFMetadata parent, RDFMetadata description, String rawRDF)
    {
        _id          = id;
        _parent      = parent;
        _children    = new HashMap<String, RDFMetadata>();
        _description = description;

        setRawRDF(rawRDF);
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

    public void setRawRDF(String rawRDF)
    {
        try
        {
            if (logger.isLoggable(Level.FINE))
                logger.log(Level.FINE, "RDF : [" + rawRDF + "]");

            _model = ModelFactory.createDefaultModel();
            Reader reader = new StringReader(rawRDF);
            _model.read(reader, null);
            reader.close();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to process RDF", throwable);
            _model = null;
        }
    }

    @Override
    public <M extends MutableMetadata> M mutableClone(Class<M> c)
    {
        return null;
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

    private String                   _id;
    private RDFMetadata              _parent;
    private Map<String, RDFMetadata> _children;
    private RDFMetadata              _description;
    private Model                    _model;
}