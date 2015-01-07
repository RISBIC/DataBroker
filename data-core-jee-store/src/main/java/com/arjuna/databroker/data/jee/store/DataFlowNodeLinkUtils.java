/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

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
public class DataFlowNodeLinkUtils
{
    private static final Logger logger = Logger.getLogger(DataFlowNodeLinkUtils.class.getName());

    public void create(String id, DataFlowNodeEntity nodeSource, DataFlowNodeEntity nodeSink, DataFlowEntity dataFlow)
    {
        DataFlowNodeLinkEntity dataFlowNodeLinkEntity = new DataFlowNodeLinkEntity(id, nodeSource, nodeSink, dataFlow);

        _entityManager.persist(dataFlowNodeLinkEntity);
    }

    public void remove(DataFlowNodeEntity nodeSource, DataFlowNodeEntity nodeSink, DataFlowEntity dataFlow)
    {
        TypedQuery<DataFlowNodeLinkEntity> query = _entityManager.createQuery("SELECT dfnl FROM DataFlowNodeLinkEntity AS dfnl WHERE (dfnl._nodeSource = :nodeSource) AND (dfnl._nodeSink = :nodeSink) AND (dfnl._dataFlow = :dataFlow)", DataFlowNodeLinkEntity.class);
        query.setParameter("nodeSource", nodeSource);
        query.setParameter("nodeSink", nodeSink);
        query.setParameter("dataFlow", dataFlow);

        DataFlowNodeLinkEntity dataFlowNodeLinkEntity = query.getSingleResult();

        if (dataFlowNodeLinkEntity != null)
            _entityManager.remove(dataFlowNodeLinkEntity);
        else
            logger.log(Level.WARNING, "Remove: Unable to find Data Flow Node Link Entity");
    }

    @PersistenceContext(unitName="Data")
    private EntityManager _entityManager;
}
