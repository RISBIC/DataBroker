/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata;

import java.util.List;

public interface MetadataContentStore
{
    public List<String> getIds();

    public String getContent(String id);

    public boolean setContent(String id, String content);

    public String getDescriptionId(String id);

    public boolean setDescriptionId(String id, String descriptionId);

    public String getParentId(String id);

    public List<String> getChildrenIds(String id);

    public String createChild(String parentId, String descriptionId, String content);
}