/*
 * Copyright (c) 2014-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.core.jee;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import com.arjuna.databroker.data.core.DataFlowLifeCycleControl;

@Startup
@Singleton
public class RecreationStartup
{
    private static final Logger logger = Logger.getLogger(RecreationStartup.class.getName());

    @PostConstruct
    public void recreationStartup()
    {
        logger.log(Level.FINE, "recreationStartup");

        _dataFlowLifeCycleControl.recreateDataFlows();
    }

    @EJB(name="DataFlowLifeCycleControl")
    private DataFlowLifeCycleControl _dataFlowLifeCycleControl;
}
