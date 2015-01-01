/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import java.lang.reflect.Type;

public interface MutableMetadataStatement extends MetadataStatement
{
    public <T> void setValue(T value, Class<T> valueClass);
    public <T> void setValue(T value, Type valueType);
}