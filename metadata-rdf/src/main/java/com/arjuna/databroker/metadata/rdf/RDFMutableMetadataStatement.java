/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.MutableMetadataStatement;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFMutableMetadataStatement extends RDFMetadataStatement implements MutableMetadataStatement
{
    public RDFMutableMetadataStatement(Statement statement)
    {
        super(statement);
    }

    @Override
    public void rename(String name)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void update(T value)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void update(String type, T value)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}