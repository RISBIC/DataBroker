/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.comms;

import java.io.Serializable;
import java.util.List;

public class DataFlowDTO implements Serializable
{
    private static final long serialVersionUID = 4022082569316938524L;

    public DataFlowDTO()
    {
    }

    public DataFlowDTO(String id)
    {
        _id                    = id;
        _name                  = null;
        _properties            = null;
        _dataFlowNodes         = null;
        _dataFlowNodeFactories = null;
        _dataFlowLinks         = null;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public PropertiesDTO getProperties()
    {
        return _properties;
    }

    public void setProperties(PropertiesDTO properties)
    {
        _properties = properties;
    }

    public List<DataFlowNodeDTO> getDataFlowNodes()
    {
        return _dataFlowNodes;
    }

    public void setDataFlowNodes(List<DataFlowNodeDTO> dataflowNodes)
    {
        _dataFlowNodes = dataflowNodes;
    }

    public List<DataFlowNodeFactoryDTO> getDataFlowNodeFactories()
    {
        return _dataFlowNodeFactories;
    }

    public void setDataFlowNodeFactories(List<DataFlowNodeFactoryDTO> dataFlowNodeFactories)
    {
        _dataFlowNodeFactories = dataFlowNodeFactories;
    }

    public List<DataFlowLinkDTO> getDataFlowLinks()
    {
        return _dataFlowLinks;
    }

    public void setDataFlowLinks(List<DataFlowLinkDTO> dataFlowLinks)
    {
        _dataFlowLinks = dataFlowLinks;
    }

    private String                       _id;
    private String                       _name;
    private PropertiesDTO                _properties;
    private List<DataFlowNodeDTO>        _dataFlowNodes;
    private List<DataFlowNodeFactoryDTO> _dataFlowNodeFactories;
    private List<DataFlowLinkDTO>        _dataFlowLinks;
}