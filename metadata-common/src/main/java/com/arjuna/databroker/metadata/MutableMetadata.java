/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

public interface MutableMetadata extends Metadata
{
    public Metadata createBlankChild(String id, Metadata description);
}