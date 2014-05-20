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
import com.arjuna.databroker.webportal.store.DataBrokerEntity;
import com.arjuna.databroker.webportal.store.DataBrokerUtils;

@SessionScoped
@ManagedBean(name="databrokerconnections")
public class DataBrokerConnectionsMO implements Serializable
{
    private static final long serialVersionUID = 5608639674146189356L;

    public DataBrokerConnectionsMO()
    {
        _dataBrokerConnections = new LinkedList<DataBrokerConnectionVO>();
    }

    public List<DataBrokerConnectionVO> getDataBrokerConnections()
    {
        return _dataBrokerConnections;
    }

    public void setDataBrokerConnections(List<DataBrokerConnectionVO> dataBrokerConnections)
    {
        _dataBrokerConnections = dataBrokerConnections;
    }

    public String doLoad()
    {
        load();

        return "databrokerconnections?faces-redirect=true";
    }

    public String doRemove(String id)
    {
        _dataBrokerUtils.removeDataBroker(id);

        load();

        return "databrokerconnections?faces-redirect=true";
    }

    public boolean load()
    {
        try
        {
            List<DataBrokerEntity> dataBrokers = _dataBrokerUtils.listDataBrokers();

            _dataBrokerConnections.clear();
            for (DataBrokerEntity dataBroker: dataBrokers)
                _dataBrokerConnections.add(new DataBrokerConnectionVO(dataBroker.getId(), dataBroker.getName(), dataBroker.getSummary(), dataBroker.getServiceRootURL(), dataBroker.getRequesterId()));

            return true;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            _dataBrokerConnections.clear();

            return false;
        }
    }

    private List<DataBrokerConnectionVO> _dataBrokerConnections;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;
}
