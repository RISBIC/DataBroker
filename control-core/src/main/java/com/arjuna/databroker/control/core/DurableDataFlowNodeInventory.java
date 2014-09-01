/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.io.Serializable;
import com.arjuna.databroker.data.DataFlowNodeInventory;
import com.arjuna.databroker.data.spi.AbstractDataFlowNodeInventory;

public class DurableDataFlowNodeInventory extends AbstractDataFlowNodeInventory implements DataFlowNodeInventory, Serializable
{
    private static final long serialVersionUID = -7282535467540305824L;

    public DurableDataFlowNodeInventory()
    {
    }
}
