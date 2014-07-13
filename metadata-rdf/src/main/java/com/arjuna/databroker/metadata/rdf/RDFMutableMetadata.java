/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.metadata.Metadata;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class RDFMutableMetadata extends RDFMetadata implements Metadata
{
    private static final Logger logger = Logger.getLogger(RDFMutableMetadata.class.getName());

    public RDFMutableMetadata(String id, RDFMetadata parent, RDFMetadata description, String rawRDF)
    {
        super(id, parent, description);
        
        setRawRDF(rawRDF);
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
}