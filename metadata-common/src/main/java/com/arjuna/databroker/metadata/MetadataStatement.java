/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import java.lang.reflect.Type;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;

public interface MetadataStatement
{
    public String getName();
    public String getType();
    public <T> T  getValue(Class<T> valueClass);
    public <T> T  getValue(Type valueType);

    public MetadataStatementSelector statement();

    public <M extends MutableMetadataStatement> M mutableClone(Class<M> c);

    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}