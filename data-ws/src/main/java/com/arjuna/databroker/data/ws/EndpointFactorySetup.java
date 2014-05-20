/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.ws;

import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;

@Startup
@Singleton
public class EndpointFactorySetup
{
    @PostConstruct
    public void setup()
    {
        EndpointDataSourceFactory endpointDataSourceFactory = new EndpointDataSourceFactory("Endpoint Data Source Factory", Collections.<String, String>emptyMap());

        _dataFlowNodeFactoryInventory.addDataFlowNodeFactory(endpointDataSourceFactory);
    }

    @EJB(lookup="java:global/server-ear-1.0.0p1m1/control-core-1.0.0p1m1/DataFlowNodeFactoryInventory")
    private DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory;
}