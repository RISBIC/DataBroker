/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StoreSetup implements Serializable
{
    private static final long serialVersionUID = 5803163943928571575L;

    @PostConstruct
    public void setup()
    {
        _dataBrokerUtils.createDataBroker("SMN - Arjuna", "Newcastle City Council Speed Management Network (Arjuna)", "http://192.168.1.65/", "arjuna");
        _dataBrokerUtils.createDataBroker("SMN - OpenShift", "Newcastle City Council Speed Management Network (OpenShift)", "http://127.11.214.129:8080/", "arjuna");
    }

    @EJB
    private DataBrokerUtils _dataBrokerUtils;
}
