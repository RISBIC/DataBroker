/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.MetadataStatement;
import com.arjuna.databroker.metadata.rdf.RDFMetadataStatement;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFMetadataStatementSelector implements MetadataStatementSelector
{
    public RDFMetadataStatementSelector(Statement statement)
    {
        _statement = statement;
    }

    @Override
    public MetadataStatement getMetadataStatement()
    {
        return new RDFMetadataStatement(_statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataStatementSelector.class))
            return (S) this;
        else
            return null;
    }

    protected Statement _statement;
}