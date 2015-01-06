/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.util.Collections;
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
public class DataFlowUtils
{
    private static final Logger logger = Logger.getLogger(DataFlowUtils.class.getName());

    public void create(String id, String name, Map<String, String> properties)
    {
        DataFlowEntity dataFlowEntity = new DataFlowEntity(id, name, properties, Collections.<DataFlowNodeEntity>emptySet(), Collections.<DataFlowNodeLinkEntity>emptySet());

        _entityManager.persist(dataFlowEntity);
    }

    public DataFlowEntity get(String id)
    {
        DataFlowEntity dataFlowEntity = _entityManager.find(DataFlowEntity.class, id);

        if (dataFlowEntity == null)
            logger.log(Level.WARNING, "Get: Unable to find Data Flow Entity");

        return dataFlowEntity;
    }

    public DataFlowEntity find(String name)
    {
        try
        {
            TypedQuery<DataFlowEntity> query = _entityManager.createQuery("SELECT df FROM DataFlowEntity AS df WHERE (df._name = :name)", DataFlowEntity.class);
            query.setParameter("name", name);

            return query.getSingleResult();
        }
        catch (Throwable throwable)
        {
        	logger.log(Level.WARNING, "Find: Unable to find Data Flow Entity: " + name, throwable);
            return null;
        }
    }

    public void remove(String id)
    {
        DataFlowEntity dataFlowEntity = _entityManager.find(DataFlowEntity.class, id);

        if (dataFlowEntity != null)
            _entityManager.remove(dataFlowEntity);
        else
            logger.log(Level.WARNING, "Remove: Unable to find Data Flow Entity");
    }

    @PersistenceContext(unitName="Data")
    private EntityManager _entityManager;
}
