/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
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
        _childNodes = Collections.emptyList();
    }

    public AdvertNodeVO(List<AdvertNodeVO> childNodes)
    {
        _childNodes = childNodes;
    }

    public List<AdvertNodeVO> getChildNodes()
    {
        return _childNodes;
    }

    public void setChildNodes(List<AdvertNodeVO> childNodes)
    {
        _childNodes = childNodes;
    }

    private List<AdvertNodeVO> _childNodes;
}
