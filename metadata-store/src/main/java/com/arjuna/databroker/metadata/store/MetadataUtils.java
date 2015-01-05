/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.store;

import java.util.LinkedList;
import java.util.List;
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
import com.arjuna.databroker.metadata.MetadataContentStore;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MetadataUtils implements MetadataContentStore
{
    private static final Logger logger = Logger.getLogger(MetadataUtils.class.getName());

    public List<String> getIds()
    {
        logger.log(Level.FINE, "MetadataUtils.getIds");

        try
        {
            TypedQuery<MetadataEntity> query = _entityManager.createQuery("SELECT m FROM MetadataEntity AS m", MetadataEntity.class);

            List<String> ids = new LinkedList<String>();
            for (MetadataEntity metadata: query.getResultList())
                ids.add(metadata.getId());

            return ids;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    public String getContent(String id)
    {
        logger.log(Level.FINE, "MetadataUtils.getContent: \"" + id + "\"");

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
        logger.log(Level.FINE, "MetadataUtils.setContent: \"" + id + "\"");

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

    @Override
    public String getDescriptionId(String id)
    {
        logger.log(Level.FINE, "MetadataUtils.getDescriptionId: \"" + id + "\"");

        try
        {
            MetadataEntity metadataEntity = _entityManager.find(MetadataEntity.class, id);
            
            if ((metadataEntity != null) && (metadataEntity.getDescription() != null))
                return metadataEntity.getDescription().getId();
            else
                return null;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setDescriptionId(String id, String descriptionId)
    {
        logger.log(Level.FINE, "MetadataUtils.setDescriptionId: \"" + id + "\",\"" + descriptionId + "\"");

        try
        {
            MetadataEntity metadataEntity    = _entityManager.find(MetadataEntity.class, id);
            MetadataEntity descriptionEntity = _entityManager.find(MetadataEntity.class, descriptionId);

            if (metadataEntity != null)
            {
                metadataEntity.setDescription(descriptionEntity);
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

    @Override
    public String getParentId(String id)
    {
        logger.log(Level.FINE, "MetadataUtils.getParentId: \"" + id + "\"");

        try
        {
            MetadataEntity metadataEntity = _entityManager.find(MetadataEntity.class, id);
            
            if ((metadataEntity != null) && (metadataEntity.getParent() != null))
                return metadataEntity.getParent().getId();
            else
                return null;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getChildrenIds(String parentId)
    {
        logger.log(Level.FINE, "MetadataUtils.getChildrenIds");

        try
        {
            MetadataEntity parent = _entityManager.find(MetadataEntity.class, parentId);

            if (parent == null)
                return null;

            TypedQuery<MetadataEntity> query = _entityManager.createQuery("SELECT m FROM MetadataEntity AS m WHERE (m._parent = :parent)", MetadataEntity.class);
            query.setParameter("parent", parent);

            List<String> childrenIds = new LinkedList<String>();
            for (MetadataEntity metadata: query.getResultList())
                childrenIds.add(metadata.getId());

            return childrenIds;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public String createChild(String parentId, String descriptionId, String content)
    {
        logger.log(Level.FINE, "MetadataUtils.createChild: \"" + parentId + "\"");

        try
        {
            MetadataEntity parentEntity      = _entityManager.find(MetadataEntity.class, parentId);
            MetadataEntity descriptionEntity = _entityManager.find(MetadataEntity.class, descriptionId);
            MetadataEntity childEntity  = new MetadataEntity();

            childEntity.setParent(parentEntity);
            childEntity.setDescription(descriptionEntity);
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