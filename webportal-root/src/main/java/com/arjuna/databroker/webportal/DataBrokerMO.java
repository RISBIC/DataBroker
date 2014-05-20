/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.DataBrokerClient;
import com.arjuna.databroker.webportal.comms.DataBrokerSummary;
import com.arjuna.databroker.webportal.comms.DataFlowFactoryClient;
import com.arjuna.databroker.webportal.comms.DataFlowNodeFactorySummary;
import com.arjuna.databroker.webportal.comms.DataFlowSummary;
import com.arjuna.databroker.webportal.store.DataBrokerEntity;
import com.arjuna.databroker.webportal.store.DataBrokerUtils;

@SessionScoped
@ManagedBean(name="databroker")
public class DataBrokerMO implements Serializable
{
    private static final long serialVersionUID = -2660820092730037733L;

    public DataBrokerMO()
    {
        _serviceRootURL               = null;
        _errorMessage                 = null;
        _dataFlowSummaries            = new LinkedList<DataFlowSummaryVO>();
        _dataFlowNodeFactorySummaries = new LinkedList<DataFlowNodeFactorySummaryVO>();
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public List<DataFlowSummaryVO> getDataFlowSummaries()
    {
        return _dataFlowSummaries;
    }

    public void setDataFlowSummaries(List<DataFlowSummaryVO> dataFlowSummaries)
    {
        _dataFlowSummaries = dataFlowSummaries;
    }

    public List<DataFlowNodeFactorySummaryVO> getDataFlowNodeFactorySummaries()
    {
        return _dataFlowNodeFactorySummaries;
    }

    public void setDataFlowNodeFactorySummaries(List<DataFlowNodeFactorySummaryVO> dataFlowNodeFactorySummaries)
    {
        _dataFlowNodeFactorySummaries = dataFlowNodeFactorySummaries;
    }

    public String doLoad(String id)
    {
        DataBrokerEntity dataBrokerEntity = _dataBrokerUtils.retrieveDataBroker(id);

        _dataFlowSummaries.clear();
        _dataFlowNodeFactorySummaries.clear();

        if (dataBrokerEntity != null)
        {
            _serviceRootURL = dataBrokerEntity.getServiceRootURL();

            reload();
        }
        else
        {
            _serviceRootURL = null;
            _errorMessage   = "Problem Loading Information";
        }

        return "databroker?faces-redirect=true";
    }

    public String doReload()
    {
        reload();

        return "databroker?faces-redirect=true";
    }

    public String doRemoveDataFlow(String dataFlowId)
    {
        removeDataFlow(dataFlowId);

        return "databroker?faces-redirect=true";
    }

    private void removeDataFlow(String dataFlowName)
    {
        _errorMessage = null;

        if (_serviceRootURL != null)
        {
            if (! _dataFlowFactoryClient.removeDataFlow(_serviceRootURL, dataFlowName))
                _errorMessage = "Unable to remove data flow \"" + dataFlowName + "\"";
        }
        else
            _errorMessage = "Unable to contact server";
    }

    public void reload()
    {
        _errorMessage = null;

        _dataFlowSummaries.clear();
        _dataFlowNodeFactorySummaries.clear();

        if (_serviceRootURL != null)
        {
            DataBrokerSummary dataBrokerSummary = _dataBrokerClient.getDataBrokerSummaries(_serviceRootURL);

            if (dataBrokerSummary != null)
            {
                for (DataFlowSummary dataFlowSummary: dataBrokerSummary.getDataFlowSummaries())
                    _dataFlowSummaries.add(new DataFlowSummaryVO(dataFlowSummary.getName()));
                for (DataFlowNodeFactorySummary dataFlowNodeFactorySummary: dataBrokerSummary.getDataFlowNodeFactorySummaries())
                    _dataFlowNodeFactorySummaries.add(new DataFlowNodeFactorySummaryVO(dataFlowNodeFactorySummary.getName(), dataFlowNodeFactorySummary.isDataSourceFactory(), dataFlowNodeFactorySummary.isDataSinkFactory(), dataFlowNodeFactorySummary.isDataProcessorFactory(), dataFlowNodeFactorySummary.isDataServiceFactory(), dataFlowNodeFactorySummary.isDataStoreFactory()));
            }
        }
    }

    private String                             _serviceRootURL;
    private String                             _errorMessage;
    private List<DataFlowSummaryVO>            _dataFlowSummaries;
    private List<DataFlowNodeFactorySummaryVO> _dataFlowNodeFactorySummaries;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;

    @EJB
    private DataBrokerClient _dataBrokerClient;

    @EJB
    private DataFlowFactoryClient _dataFlowFactoryClient;
}
