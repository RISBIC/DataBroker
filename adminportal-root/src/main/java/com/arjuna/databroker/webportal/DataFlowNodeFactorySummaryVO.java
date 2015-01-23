/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DataFlowNodeFactorySummaryVO implements Serializable
{
    private static final long serialVersionUID = -3505678911546407629L;

    public DataFlowNodeFactorySummaryVO()
    {
    }

    public DataFlowNodeFactorySummaryVO(String name, Map<String, String> properties, Boolean dataSourceFactory, Boolean dataSinkFactory, Boolean dataProcessorFactory, Boolean dataServiceFactory, Boolean dataStoreFactory)
    {
        _name                 = name;
        _properties           = PropertyVO.fromMap(properties);
        _dataSourceFactory    = dataSourceFactory;
        _dataSinkFactory      = dataSinkFactory;
        _dataProcessorFactory = dataProcessorFactory;
        _dataServiceFactory   = dataServiceFactory;
        _dataStoreFactory     = dataStoreFactory;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public List<PropertyVO> getProperties()
    {
        return _properties;
    }

    public void setProperties(List<PropertyVO> properties)
    {
        _properties = properties;
    }

    public boolean getDataSourceFactory()
    {
        return _dataSourceFactory;
    }

    public void setDataSourceFactory(boolean dataSourceFactory)
    {
        _dataSourceFactory = dataSourceFactory;
    }

    public boolean getDataSinkFactory() {
        return _dataSinkFactory;
    }

    public void setDataSinkFactory(boolean dataSinkFactory)
    {
        _dataSinkFactory = dataSinkFactory;
    }

    public boolean getDataProcessorFactory() {
        return _dataProcessorFactory;
    }

    public void setDataProcessorFactory(boolean dataProcessorFactory)
    {
        _dataProcessorFactory = dataProcessorFactory;
    }

    public boolean getDataServiceFactory()
    {
        return _dataServiceFactory;
    }

    public void setDataServiceFactory(boolean dataServiceFactory)
    {
        _dataServiceFactory = dataServiceFactory;
    }

    public boolean getDataStoreFactory()
    {
        return _dataStoreFactory;
    }

    public void setDataStoreFactory(boolean dataStoreFactory)
    {
        _dataStoreFactory = dataStoreFactory;
    }

    private String           _name;
    private List<PropertyVO> _properties;
    private boolean          _dataSourceFactory;
    private boolean          _dataSinkFactory;
    private boolean          _dataProcessorFactory;
    private boolean          _dataServiceFactory;
    private boolean          _dataStoreFactory;
}
