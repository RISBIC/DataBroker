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
        if (_dataBrokerUtils.listDataBrokers().size() == 0)
        {
            _dataBrokerUtils.createDataBroker("SMN - Arjuna", "Newcastle City Council Speed Management Network (Arjuna)", "http://192.168.1.65/", "arjuna");
            _dataBrokerUtils.createDataBroker("SMN - OpenShift", "Newcastle City Council Speed Management Network (OpenShift)", "http://127.11.214.129:8080/", "arjuna");
            _dataBrokerUtils.createDataBroker("Soft-DB - OpenShift", "Sunderland City Council Geodata (OpenShift)", "http://127.8.113.1:8080/", "arjuna");
        }

        if (_usersUtils.listUsers().size() == 0)
        {
            _usersUtils.createUser("Default Admin", "Default Password", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
            _usersUtils.createUser("guest", "guest", Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
        }
    }

    @EJB
    private UsersUtils _usersUtils;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;
}
