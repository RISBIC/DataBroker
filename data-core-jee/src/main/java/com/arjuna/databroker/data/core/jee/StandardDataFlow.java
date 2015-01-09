/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.Map;

public class StandardDataFlow extends AbstractJEEDataFlow
{
    public StandardDataFlow()
    {
        super(null, null);
    }

    public StandardDataFlow(String name, Map<String, String> properties)
    {
        super(name, properties);
    }
}
