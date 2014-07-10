/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MetadataStatement;

public interface MetadataStatementSelector
{
    public <T> MetadataStatement<T> getStatement();

    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}