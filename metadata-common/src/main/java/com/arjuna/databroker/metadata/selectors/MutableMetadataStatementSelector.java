/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.selectors;

import com.arjuna.databroker.metadata.MutableMetadataStatement;

public interface MutableMetadataStatementSelector extends MetadataStatementSelector
{
    public MutableMetadataStatement getMutableMetadataStatement();

    public <S extends MutableMetadataStatementSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException;
}