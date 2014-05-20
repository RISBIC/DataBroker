/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.store;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MetadataUtils
{
    private static final Logger logger = Logger.getLogger(MetadataUtils.class.getName());

    public String getContent(String id)
    {
        logger.fine("MetadataUtils.getContent: \"" + id + "\"");

        try
        {
            MetadataEntity metadataEntity = _entityManager.find(MetadataEntity.class, id);
            
            if (metadataEntity != null)
                return metadataEntity.getContent();
            else
                return null;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    public boolean setContent(String id, String content)
    {
        logger.fine("MetadataUtils.setContent: \"" + id + "\"");

        try
        {
            MetadataEntity metadataEntity = _entityManager.find(MetadataEntity.class, id);

            if (metadataEntity != null)
            {
                metadataEntity.setContent(content);
                _entityManager.merge(metadataEntity);

                return true;
            }
            else
                return false;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return false;
        }
    }

    public String createChild(String parentId, String content)
    {
        logger.fine("MetadataUtils.setContent: \"" + parentId + "\"");

        try
        {
            MetadataEntity parentEntity = _entityManager.find(MetadataEntity.class, parentId);
            MetadataEntity childEntity  = new MetadataEntity();

            childEntity.setParent(parentEntity);
            childEntity.setContent(content);

            _entityManager.persist(childEntity);

            return childEntity.getId().toString();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return "";
        }
    }

    @PersistenceContext(unitName="Metadata")
    private EntityManager _entityManager;
}