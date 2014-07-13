/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;

public interface MetadataStatement
{
    public String getName();
    public String getType();
    public <T> T  getValue(Class<T> valueClass);

    public MetadataStatementSelector statement();

    public <M extends MutableMetadataStatement> M mutableClone(Class<M> c);

    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}