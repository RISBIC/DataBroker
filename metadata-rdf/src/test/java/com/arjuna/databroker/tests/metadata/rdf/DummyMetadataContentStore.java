/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.metadata.MetadataContentStore;

public class DummyMetadataContentStore implements MetadataContentStore
{
    private static final Logger logger = Logger.getLogger(DummyMetadataContentStore.class.getName());

    public DummyMetadataContentStore(List<String> ids, Map<String, String> contentMap, Map<String, String> descriptionIdMap, Map<String, String> parentIdMap, Map<String, List<String>> childrenIdsMap)
    {
        _ids              = ids;
        _contentMap       = contentMap;
        _descriptionIdMap = descriptionIdMap;
        _parentIdMap      = parentIdMap;
        _childrenIdsMap   = childrenIdsMap;
    }

    public List<String> getIds()
    {
        logger.log(Level.FINE, "InMemoryMetadataContentStore.getIds");

        return _ids;
    }

    public String getContent(String id)
    {
        logger.log(Level.FINE, "MetadataUtils.getContent: \"" + id + "\"");

        return _contentMap.get(id);
    }

    public boolean setContent(String id, String content)
    {
        logger.log(Level.FINE, "MetadataUtils.setContent: \"" + id + "\"");

        throw new UnsupportedOperationException();
    }

    @Override
    public String getDescriptionId(String id)
    {
        logger.log(Level.FINE, "MetadataUtils.getDescriptionId: \"" + id + "\"");

        return _descriptionIdMap.get(id);
    }

    @Override
    public boolean setDescriptionId(String id, String descriptionId)
    {
        logger.log(Level.FINE, "MetadataUtils.setDescriptionId: \"" + id + "\",\"" + descriptionId + "\"");

        throw new UnsupportedOperationException();
    }

    @Override
    public String getParentId(String id)
    {
        logger.log(Level.FINE, "MetadataUtils.getParentId: \"" + id + "\"");

        return _parentIdMap.get(id);
     }

    @Override
    public List<String> getChildrenIds(String parentId)
    {
        logger.log(Level.FINE, "MetadataUtils.getChildrenIds");

        return _childrenIdsMap.get(parentId);
    }

    @Override
    public void createOverwrite(String id, String content)
    {
        logger.log(Level.FINE, "MetadataUtils.createOverwrite: \"" + id + "\"");

        throw new UnsupportedOperationException();
    }

    @Override
    public String createChild(String parentId, String descriptionId, String content)
    {
        logger.log(Level.FINE, "MetadataUtils.createChild: \"" + parentId + "\", \"" + descriptionId + "\"");

        throw new UnsupportedOperationException();
    }

    private List<String>              _ids;
    private Map<String, String>       _contentMap;
    private Map<String, String>       _descriptionIdMap;
    private Map<String, String>       _parentIdMap;
    private Map<String, List<String>> _childrenIdsMap;
}