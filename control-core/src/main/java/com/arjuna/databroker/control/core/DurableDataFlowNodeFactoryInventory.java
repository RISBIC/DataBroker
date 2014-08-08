/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core;

import java.io.Serializable;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.spi.AbstractDataFlowNodeFactoryInventory;

public class DurableDataFlowNodeFactoryInventory extends AbstractDataFlowNodeFactoryInventory implements DataFlowNodeFactoryInventory, Serializable
{
    private static final long serialVersionUID = -6015427308480999113L;

    public DurableDataFlowNodeFactoryInventory()
    {
    }
}
