/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
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
import com.arjuna.databroker.webportal.comms.AdvertClient;
import com.arjuna.databroker.webportal.comms.AdvertSummary;

@SessionScoped
@ManagedBean(name="advert")
public class AdvertMO implements Serializable
{
    private static final long serialVersionUID = -4870758036994307897L;

    private static final Logger logger = Logger.getLogger(AdvertMO.class.getName());

    public AdvertMO()
    {
        _serviceRootURLs = null;
        _requesterIds    = null;
        _userId          = null;
        _adverts         = new LinkedList<AdvertVO>();
        _errorMessage    = null;
    }

    public List<String> getServiceRootURLs()
    {
        return _serviceRootURLs;
    }

    public void setServiceRootURLs(List<String> serviceRootURLs)
    {
        _serviceRootURLs = serviceRootURLs;
    }

    public List<String> getRequesterIds()
    {
        return _requesterIds;
    }

    public void setRequesterIds(List<String> requesterIds)
    {
        _requesterIds = requesterIds;
    }

    public String getUserId()
    {
        return _userId;
    }

    public void setUserId(String userId)
    {
        _userId = userId;
    }

    public List<AdvertVO> getAdverts()
    {
        return _adverts;
    }

    public void setAdverts(List<AdvertVO> adverts)
    {
        _adverts = adverts;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doLoad(List<String> serviceRootURLs, List<String> requesterIds, String userId)
    {
        logger.log(Level.FINE, "AdvertMO.doLoad: " + _serviceRootURLs + ", " + _requesterIds + ", " + userId);

        _serviceRootURLs = serviceRootURLs;
        _requesterIds    = requesterIds;
        _userId          = userId;

        load();

        return "/dataadverts/dataview?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "AdvertMO.doReload: " + _serviceRootURLs + ", " + _requesterIds + ", " + _userId);

        load();

        return "/dataadverts/dataview?faces-redirect=true";
    }

    public void load()
    {
        logger.log(Level.FINE, "AdvertMO.load");
        try
        {
            _adverts.clear();
            if ((_serviceRootURLs == null) || (_requesterIds == null))
                _errorMessage = "Null 'service root URLs' or 'requester ids'";
            else if (_serviceRootURLs.size() != _requesterIds.size())
                _errorMessage = "Size mismatch between 'service root URLs' or 'requester ids'";
            for (int index = 0; index < _serviceRootURLs.size(); index++)
            {
                List<AdvertSummary> advertSummaries = _advertClient.getAdverts(_serviceRootURLs.get(index), _requesterIds.get(index), _userId);
                for (AdvertSummary advertSummary: advertSummaries)
                    _adverts.add(new AdvertVO(advertSummary.getServiceURL(), advertSummary.getMetadataId(), advertSummary.getMetadataPath(), advertSummary.isRootNode(), null));
            }
            _errorMessage = null;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem getting metadata ids", throwable);
            _adverts.clear();
            _errorMessage = "Problem getting metadata ids";
        }
    }

    private List<String>   _serviceRootURLs;
    private List<String>   _requesterIds;
    private String         _userId;
    private List<AdvertVO> _adverts;
    private String         _errorMessage;

    @EJB
    private AdvertClient _advertClient;
}
