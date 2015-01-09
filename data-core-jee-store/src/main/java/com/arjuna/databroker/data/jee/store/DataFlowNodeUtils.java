/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
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
public class DataFlowNodeUtils
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeUtils.class.getName());

    public DataFlowNodeEntity find(String name, DataFlowEntity dataFlow)
    {
        try
        {
            TypedQuery<DataFlowNodeEntity> query = _entityManager.createQuery("SELECT dfn FROM DataFlowNodeEntity AS dfn WHERE (dfn._name = :name) AND (dfn._dataFlow = :dataFlow)", DataFlowNodeEntity.class);
            query.setParameter("name", name);
            query.setParameter("dataFlow", dataFlow);

            return query.getSingleResult();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Find: Unable to find Data Flow Entity: " + name, throwable);
            return null;
        }
    }

    public void create(String id, String name, Map<String, String> properties, String nodeClassName, DataFlowEntity dataFlow, Serializable state)
    {
        DataFlowNodeEntity dataFlowNodeEntity = new DataFlowNodeEntity(id, name, properties, nodeClassName, dataFlow, state);

        _entityManager.persist(dataFlowNodeEntity);
    }

    public Serializable getState(String id)
    {
        DataFlowNodeEntity dataFlowNodeEntity = _entityManager.find(DataFlowNodeEntity.class, id);

        if (dataFlowNodeEntity != null)
            return dataFlowNodeEntity.getState();
        else
        {
            logger.log(Level.WARNING, "Get State: Unable to find Data Flow Node Entity");
            return null;
        }
    }

    public void setState(String id, Serializable state)
    {
        DataFlowNodeEntity dataFlowNodeEntity = _entityManager.find(DataFlowNodeEntity.class, id);

        if (dataFlowNodeEntity != null)
        {
            dataFlowNodeEntity.setState(state);

            _entityManager.merge(dataFlowNodeEntity);
        }
        else
            logger.log(Level.WARNING, "Set State: Unable to find Data Flow Node Entity");
    }

    public void remove(String name, DataFlowEntity dataFlow)
    {
        TypedQuery<DataFlowNodeEntity> query = _entityManager.createQuery("SELECT dfn FROM DataFlowNodeEntity AS dfn WHERE (dfn._name = :name) AND (dfn._dataFlow = :dataFlow)", DataFlowNodeEntity.class);
        query.setParameter("name", name);
        query.setParameter("dataFlow", dataFlow);

        DataFlowNodeEntity dataFlowNodeEntity = query.getSingleResult();

        if (dataFlowNodeEntity != null)
            _entityManager.remove(dataFlowNodeEntity);
        else
            logger.log(Level.WARNING, "Remove: Unable to find Data Flow Node Entity");
    }

    @PersistenceContext(unitName="Data")
    private EntityManager _entityManager;
}
