/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DataBrokerUtils
{
    private static final Logger logger = Logger.getLogger(DataBrokerUtils.class.getName());

    public List<DataBrokerEntity> listDataBrokers()
    {
        logger.fine("DataBrokerUtils.listDataBrokers");

        try
        {
            TypedQuery<DataBrokerEntity> query = _entityManager.createQuery("SELECT db FROM DataBrokerEntity AS db ORDER BY db._name ASC", DataBrokerEntity.class);

            return query.getResultList();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void createDataBroker(String name, String summary, String serviceRootURL, String requesterId)
    {
        logger.fine("DataBrokerUtils.createDataBroker: " + name + ", " + summary + ", " + serviceRootURL + ", " + requesterId);

        DataBrokerEntity dataBroker = new DataBrokerEntity(name, summary, serviceRootURL, requesterId);

        logger.fine("DataBrokerUtils.createDataBroker: " + dataBroker.getName() + ", " + dataBroker.getSummary() + ", " + dataBroker.getServiceRootURL() + ", " + dataBroker.getRequesterId());

        _entityManager.persist(dataBroker);
    }

    public DataBrokerEntity retrieveDataBroker(String id)
    {
        logger.fine("DataBrokerUtils.retrieveDataBroker: " + id);

        return _entityManager.find(DataBrokerEntity.class, id);
    }

    public void replaceDataBroker(String id, String name, String summary, String serviceRootURL, String requesterId)
    {
        logger.fine("DataBrokerUtils.replaceDataBroker: " + id + ", " + name + ", " + summary + ", " + serviceRootURL + ", " + requesterId);

        DataBrokerEntity dataBroker = _entityManager.find(DataBrokerEntity.class, id);
        dataBroker.setName(name);
        dataBroker.setSummary(summary);
        dataBroker.setServiceRootURL(serviceRootURL);
        dataBroker.setRequesterId(requesterId);

        _entityManager.merge(dataBroker);
    }

    public void removeDataBroker(String id)
    {
        logger.fine("DataBrokerUtils.removeDataBroker: " + id);

        DataBrokerEntity dataBroker = _entityManager.find(DataBrokerEntity.class, id);

        _entityManager.remove(dataBroker);
    }

    @PersistenceContext(unitName="WebPortal")
    private EntityManager _entityManager;
}