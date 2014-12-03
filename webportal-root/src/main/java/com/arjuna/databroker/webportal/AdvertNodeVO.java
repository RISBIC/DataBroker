/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class AdvertNodeVO implements Serializable
{
    private static final long serialVersionUID = 5513196753143529168L;

    public AdvertNodeVO()
    {
        _metadataPath = null;
        _childNodes   = Collections.emptyList();
    }

    public String getMetadataPath()
    {
        return _metadataPath;
    }

    public void setMetadataPath(String metadataPath)
    {
        _metadataPath = metadataPath;
    }

    public AdvertNodeVO(String metadataPath, List<AdvertNodeVO> childNodes)
    {
        _metadataPath = metadataPath;
        _childNodes   = childNodes;
    }

    public List<AdvertNodeVO> getChildNodes()
    {
        return _childNodes;
    }

    public void setChildNodes(List<AdvertNodeVO> childNodes)
    {
        _childNodes = childNodes;
    }

    private String             _metadataPath;
    private List<AdvertNodeVO> _childNodes;
}
