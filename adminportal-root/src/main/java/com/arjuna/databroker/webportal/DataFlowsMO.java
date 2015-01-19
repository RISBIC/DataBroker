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
import com.arjuna.databroker.webportal.store.DataBrokerEntity;
import com.arjuna.databroker.webportal.store.DataBrokerUtils;

@SessionScoped
@ManagedBean(name="dataflows")
public class DataFlowsMO implements Serializable
{
    private static final long serialVersionUID = 8838447133924702147L;

    private static final Logger logger = Logger.getLogger(DataFlowsMO.class.getName());

    public DataFlowsMO()
    {
        _dataFlows = new LinkedList<DataBrokerConnectionVO>();
    }

    public List<DataBrokerConnectionVO> getDataFlows()
    {
        return _dataFlows;
    }

    public String doLoad()
    {
        load();

        return "/dataflows/dataflows?faces-redirect=true";
    }

    public boolean load()
    {
        try
        {
            synchronized (_dataFlows)
            {
                List<DataBrokerEntity> dataBrokers = _dataBrokerUtils.listDataBrokers();

                _dataFlows.clear();
                for (DataBrokerEntity dataBroker: dataBrokers)
                    _dataFlows.add(new DataBrokerConnectionVO(dataBroker.getId().toString(), dataBroker.getName(), dataBroker.getSummary(), dataBroker.getServiceRootURL(), dataBroker.getRequesterId()));
            }

            return true;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Load failed to list DataBrokers", throwable);
            _dataFlows.clear();

            return false;
        }
    }

    private List<DataBrokerConnectionVO> _dataFlows;

    @EJB
    private DataBrokerUtils _dataBrokerUtils;
}
