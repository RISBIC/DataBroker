/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;

public interface MetadataContent
{
    public <V> V getView(Class<V> viewInterface)
            throws IllegalArgumentException;

    public MetadataStatementSelector  statement(String name, String type);
    public MetadataStatementsSelector statements();

    public <M extends MutableMetadataContent> M mutableClone(Class<M> c);

    public <S extends MetadataContentSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}