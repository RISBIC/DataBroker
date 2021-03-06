/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.DataBrokerClient;
import com.arjuna.databroker.webportal.comms.DataBrokerSummary;
import com.arjuna.databroker.webportal.comms.DataFlowFactoryClient;
import com.arjuna.databroker.webportal.comms.DataFlowNodeFactorySummary;
import com.arjuna.databroker.webportal.comms.DataFlowSummary;
import com.arjuna.databroker.webportal.comms.RequestProblemException;
import com.arjuna.databroker.webportal.store.DataBrokerEntity;
import com.arjuna.databroker.webportal.store.DataBrokerUtils;

@SessionScoped
@ManagedBean(name="databroker")
public class DataBrokerMO implements Serializable
{
    private static final Logger logger = Logger.getLogger(DataBrokerMO.class.getName());

    private static final long serialVersionUID = -2660820092730037733L;

    public DataBrokerMO()
    {
        _serviceRootURL               = null;
        _dataFlowSummaries            = new LinkedList<DataFlowSummaryVO>();
        _dataFlowNodeFactorySummaries = new LinkedList<DataFlowNodeFactorySummaryVO>();
        _selectedDataFlowNodeFactory  = null;
        _errorMessage                 = null;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
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

    public DataFlowNodeFactorySummaryVO getSelectedDataFlowNodeFactory()
    {
        return _selectedDataFlowNodeFactory;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doLoad(String id)
    {
        logger.log(Level.FINE, "DataBrokerMO.doLoad: " + id);

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

        return "/dataflows/databroker_dataflows?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "DataBrokerMO.doReload");

        reload();

        return "#";
    }

    public String doReloadToDataFlowsTab()
    {
        logger.log(Level.FINE, "DataBrokerMO.doReloadToDataFlowsTab");

        reload();

        return "/dataflows/databroker_dataflows?faces-redirect=true";
    }

    public String doToDataFlowsTab()
    {
        return "/dataflows/databroker_dataflows?faces-redirect=true";
    }

    public String doToDataFlowNodeFactoriesTab()
    {
        return "/dataflows/databroker_dataflownodefactories?faces-redirect=true";
    }

    public String doRemoveDataFlow(String dataFlowId)
    {
        removeDataFlow(dataFlowId);

        reload();

        return "/dataflows/databroker_dataflows?faces-redirect=true";
    }

    public String doExamineDataFlowNodeFactory(String dataFlowNodeFactoryName)
    {
        logger.log(Level.FINE, "DataBrokerMO.doExamineDataFlowNodeFactory: [" + dataFlowNodeFactoryName + "]");

        try
        {
            _errorMessage = null;
            for (DataFlowNodeFactorySummaryVO dataFlowNodeFactorySummary: _dataFlowNodeFactorySummaries)
                for (PropertyVO property: dataFlowNodeFactorySummary.getAttributes())
                    if (property.getName().equals("Name") && property.getValue().equals(dataFlowNodeFactoryName))
                        _selectedDataFlowNodeFactory = dataFlowNodeFactorySummary;
        }
        catch (Throwable throwable)
        {
            _errorMessage = "Problem obtaining information about selected data flow node factory";
        }

        return "/dataflows/databroker_dataflownodefactory_attributes?faces-redirect=true";
    }

    private void removeDataFlow(String dataFlowName)
    {
        _errorMessage = null;

        if (_serviceRootURL != null)
        {
            try
            {
                if (! _dataFlowFactoryClient.removeDataFlow(_serviceRootURL, dataFlowName))
                     _errorMessage = "Unable to remove data flow \"" + dataFlowName + "\"";
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }
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
            DataBrokerSummary dataBrokerSummary = null;
            try
            {
                dataBrokerSummary = _dataBrokerClient.getDataBrokerSummaries(_serviceRootURL);
            }
            catch (RequestProblemException requestProblemException)
            {
                _errorMessage = requestProblemException.getMessage();
            }

            if (dataBrokerSummary != null)
            {
                for (DataFlowSummary dataFlowSummary: dataBrokerSummary.getDataFlowSummaries())
                    _dataFlowSummaries.add(new DataFlowSummaryVO(dataFlowSummary.getName()));
                for (DataFlowNodeFactorySummary dataFlowNodeFactorySummary: dataBrokerSummary.getDataFlowNodeFactorySummaries())
                    _dataFlowNodeFactorySummaries.add(new DataFlowNodeFactorySummaryVO(dataFlowNodeFactorySummary.getAttributes(), dataFlowNodeFactorySummary.getProperties(), dataFlowNodeFactorySummary.isDataSourceFactory(), dataFlowNodeFactorySummary.isDataSinkFactory(), dataFlowNodeFactorySummary.isDataProcessorFactory(), dataFlowNodeFactorySummary.isDataServiceFactory(), dataFlowNodeFactorySummary.isDataStoreFactory()));
            }
            else
                _errorMessage = "Unable to connect to DataBroker for information";
        }
        else
            _errorMessage = "No DataBroker specified";
    }

    private String                             _serviceRootURL;
    private List<DataFlowSummaryVO>            _dataFlowSummaries;
    private List<DataFlowNodeFactorySummaryVO> _dataFlowNodeFactorySummaries;
    private DataFlowNodeFactorySummaryVO       _selectedDataFlowNodeFactory;
    private String                             _errorMessage;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;

    @EJB
    private DataBrokerClient _dataBrokerClient;

    @EJB
    private DataFlowFactoryClient _dataFlowFactoryClient;
}
