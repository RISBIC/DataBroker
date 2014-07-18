/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import com.arjuna.databroker.metadata.MetadataStatement;
import com.arjuna.databroker.metadata.MutableMetadataStatement;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFMetadataStatement implements MetadataStatement
{
    public RDFMetadataStatement(Statement statement)
    {
        _statement = statement;
    }

    @Override
    public String getName()
    {
        return _statement.getSubject().getURI();
    }

    @Override
    public String getType()
    {
        return _statement.getPredicate().getURI();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> valueClass)
    {
        if ((_statement != null) && valueClass.isAssignableFrom(String.class))
            return (T) _statement.getString();
        else
            return null;
    }

    @Override
    public MetadataStatementSelector statement()
    {
        return new RDFMetadataStatementSelector(_statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadataStatement> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(RDFMutableMetadataContent.class))
            return (M) new RDFMutableMetadataStatement(_statement);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataStatementSelector.class))
            return (S) new RDFMetadataStatementSelector(_statement);
        else
            return null;
    }

    protected Statement _statement;
}