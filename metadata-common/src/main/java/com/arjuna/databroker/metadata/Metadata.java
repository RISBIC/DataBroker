/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import java.util.UUID;
import com.arjuna.databroker.metadata.selectors.MetadataContextSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;

public interface Metadata
{
    public UUID getID();

    public Metadata createChild();

    public MetadataSelector self();
    public MetadataSelector  parent();
    public MetadatasSelector children();
    public MetadataContextSelector    state();
}