/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import com.arjuna.databroker.metadata.selectors.MutableMetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementsSelector;

public interface MutableMetadataContent extends MetadataContent
{
    public MutableMetadataStatementSelector  mutableStatement(String name, String type);
    public MutableMetadataStatementsSelector mutableStatements();

    public <S extends MutableMetadataContentSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
    
    public <T> void addMetadataStatement(String name, T value);
    public <T> void removeMetadataStatement(String name);
}