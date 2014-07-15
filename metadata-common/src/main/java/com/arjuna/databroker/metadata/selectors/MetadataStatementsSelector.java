/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

public interface MetadataStatementsSelector
{
    public MetadataStatementSelector statement(String name, String type);

    public <S extends MetadataStatementsSelector> S selector(Class<S> c)
        throws IllegalArgumentException;
}