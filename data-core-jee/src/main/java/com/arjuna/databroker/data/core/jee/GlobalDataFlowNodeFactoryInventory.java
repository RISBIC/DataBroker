/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import javax.ejb.Singleton;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.spi.AbstractDataFlowNodeFactoryInventory;

@Singleton(name="DataFlowNodeFactoryInventory")
public class GlobalDataFlowNodeFactoryInventory extends AbstractDataFlowNodeFactoryInventory implements DataFlowNodeFactoryInventory
{
    public GlobalDataFlowNodeFactoryInventory()
    {
    }
}
