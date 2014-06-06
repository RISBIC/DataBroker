/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core.nano;

import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.spi.AbstractDataFlowNodeFactoryInventory;

public class GlobalDataFlowNodeFactoryInventory extends AbstractDataFlowNodeFactoryInventory implements DataFlowNodeFactoryInventory
{
    public GlobalDataFlowNodeFactoryInventory()
    {
    }

    public DataFlowNodeFactoryInventory getInstance()
    {
        synchronized (_syncObject)
        {
            if (_dataFlowNodeFactoryInventory == null)
                _dataFlowNodeFactoryInventory = new GlobalDataFlowNodeFactoryInventory();

            return _dataFlowNodeFactoryInventory;
        }
    }
    
    private static Object                       _syncObject                   = new Object();
    private static DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory = null;
}
