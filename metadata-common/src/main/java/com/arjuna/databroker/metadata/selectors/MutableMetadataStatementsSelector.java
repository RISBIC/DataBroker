/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

public interface MutableMetadataStatementsSelector extends MetadataStatementsSelector
{
    public MutableMetadataStatementSelector mutableStatement(String name, String type);

    public <S extends MutableMetadataStatementsSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
}