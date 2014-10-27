/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.arjuna.databroker.metadata.MutableMetadataStatement;
import com.arjuna.databroker.metadata.rdf.RDFMutableMetadataStatement;
import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementSelector;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFMutableMetadataStatementSelector extends RDFMetadataStatementSelector implements MutableMetadataStatementSelector
{
    public RDFMutableMetadataStatementSelector(Statement statement)
    {
        super(statement);
    }

    @Override
    public MutableMetadataStatement getMutableMetadataStatement()
    {
        return new RDFMutableMetadataStatement(_statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MutableMetadataStatementSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMutableMetadataStatementSelector.class))
            return (S) this;
        else
            return null;
    }
}