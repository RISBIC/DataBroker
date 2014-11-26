/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.store.DataBrokerEntity;
import com.arjuna.databroker.webportal.store.DataBrokerUtils;

@SessionScoped
@ManagedBean(name="dataviews")
public class DataViewsMO implements Serializable
{
    private static final long serialVersionUID = 1726666225697565917L;

    public DataViewsMO()
    {
        _dataViews = new LinkedList<DataBrokerConnectionVO>();
        _dataViewConnectionURLs = new LinkedList<String>();
        _dataViewRequesterIds = new LinkedList<String>();
}

    public List<DataBrokerConnectionVO> getDataViews()
    {
        return _dataViews;
    }

    public void setDataViews(List<DataBrokerConnectionVO> dataViews)
    {
        _dataViews = dataViews;
    }

    public List<String> getDataViewConnectionURLs()
    {
        return _dataViewConnectionURLs;
    }

    public void setDataViewConnectionURLs(List<String> dataViewConnectionURLs)
    {
        _dataViewConnectionURLs = dataViewConnectionURLs;
    }

    public List<String> getDataViewRequesterIds()
    {
        return _dataViewRequesterIds;
    }

    public void setDataViewRequesterIds(List<String> dataViewRequesterIds)
    {
        _dataViewRequesterIds = dataViewRequesterIds;
    }

    public String doLoad()
    {
        load();

        return "/dataviews/dataadverts?faces-redirect=true";
    }

    public boolean load()
    {
        try
        {
            List<DataBrokerEntity> dataBrokers = _dataBrokerUtils.listDataBrokers();

            _dataViews.clear();
            _dataViewConnectionURLs.clear();
            _dataViewRequesterIds.clear();

            for (DataBrokerEntity dataBroker: dataBrokers) {
                _dataViews.add(new DataBrokerConnectionVO(dataBroker.getId().toString(), dataBroker.getName(), dataBroker.getSummary(), dataBroker.getServiceRootURL(), dataBroker.getRequesterId()));
                _dataViewConnectionURLs.add(dataBroker.getServiceRootURL());
                _dataViewRequesterIds.add(dataBroker.getRequesterId());
            }

            return true;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            _dataViews.clear();

            return false;
        }
    }

    private List<DataBrokerConnectionVO> _dataViews;
    private List<String> _dataViewConnectionURLs;
    private List<String> _dataViewRequesterIds;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;
}
