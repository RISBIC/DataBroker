/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.AdvertClient;
import com.arjuna.databroker.webportal.comms.AdvertNodeSummary;

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

    public String getAdvertsJSON()
    {
        return _advertsJSON;
    }

    public void setAdvertsJSON(String advertsJSON)
    {
        _advertsJSON = advertsJSON;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String doLoad(String serviceRootURL, String requesterId, String userId)
    {
        logger.log(Level.FINE, "AdvertMO.doLoad: " + serviceRootURL + ", " + requesterId + ", " + userId);

        _serviceRootURLs = new LinkedList<String>();
        _requesterIds    = new LinkedList<String>();
        Collections.addAll(_serviceRootURLs, serviceRootURL);
        Collections.addAll(_requesterIds, requesterId);
        _userId = userId;

        load();

        return "/dataviews/dataadvert?faces-redirect=true";
    }

    public String doLoad(List<String> serviceRootURLs, List<String> requesterIds, String userId)
    {
        logger.log(Level.FINE, "AdvertMO.doLoad: " + serviceRootURLs + ", " + requesterIds + ", " + userId);
 
        _serviceRootURLs = serviceRootURLs;
        _requesterIds    = requesterIds;
        _userId          = userId;

        load();

        return "/dataviews/dataadvert?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "AdvertMO.doReload: " + _serviceRootURLs + ", " + _requesterIds + ", " + _userId);

        load();

        return "/dataadverts/dataadvert?faces-redirect=true";
    }

    private void load()
    {
        logger.log(Level.FINE, "AdvertMO.load");
        try
        {
            _adverts.clear();
            _advertsJSON  = null;
            _errorMessage = null;
            if ((_serviceRootURLs == null) || (_requesterIds == null))
                _errorMessage = "Null 'service root URLs' or 'requester ids'";
            else if (_serviceRootURLs.size() != _requesterIds.size())
                _errorMessage = "Size mismatch between 'service root URLs' or 'requester ids'";
            else
            {
                for (int index = 0; index < _serviceRootURLs.size(); index++)
                {
                    String serviceRootURL = _serviceRootURLs.get(index);
                    String requesterId    = _requesterIds.get(index);

                    List<AdvertNodeSummary> advertNodeSummaries = _advertClient.getAdverts(serviceRootURL, requesterId, _userId);

                    if (advertNodeSummaries != null)
                    {
                        Map<String, AdvertNodeVO> advertNodeMap = new HashMap<String, AdvertNodeVO>();
                        for (AdvertNodeSummary advertNodeSummary: advertNodeSummaries)
                            advertNodeMap.put(advertNodeSummary.getId(), new AdvertStandardNodeVO(advertNodeSummary.getNodeClass(), advertNodeSummary.getName(), advertNodeSummary.getSummary(), advertNodeSummary.getDiscription(), advertNodeSummary.getDateCreated(), advertNodeSummary.getDateUpdate(), advertNodeSummary.getOwner(), advertNodeSummary.getTags(), null));

                        for (AdvertNodeSummary advertNodeSummary: advertNodeSummaries)
                        {
                            List<AdvertNodeVO> childNodes = new LinkedList<AdvertNodeVO>();
                            for (String childNodeId: advertNodeSummary.getChildNodeIds())
                                childNodes.add(advertNodeMap.get(childNodeId));
                            advertNodeMap.get(advertNodeSummary.getId()).setChildNodes(childNodes);
                        }

                        for (AdvertNodeSummary advertNodeSummary: advertNodeSummaries)
                            _adverts.add(new AdvertVO(serviceRootURL, requesterId, advertNodeSummary.getMetadataId(), advertNodeSummary.getMetadataPath(), advertNodeSummary.getIsRootNode(), advertNodeMap.get(advertNodeSummary.getId())));
                    }
                    else
                        _errorMessage = "Failed to obtain adverts from \"" + _serviceRootURLs + "\"";
                }

                _advertsJSON = advertsToJSON(_adverts);
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem getting metadata ids", throwable);
            _adverts.clear();
            _errorMessage = "Problem getting metadata ids";
        }
    }

    private String advertsToJSON(List<AdvertVO> adverts)
    {
        StringBuffer result = new StringBuffer();

        result.append("'{ \"root\" : \"The Root\", \"children\": [ ");
        boolean firstAdvert = true;
        for (AdvertVO advert: _adverts)
            if (advert.getIsRootNode())
            {
                if (firstAdvert)
                    firstAdvert = false;
                else
                    result.append(", ");
                advertsToJSON(result, advert, (AdvertStandardNodeVO) advert.getNode());
            }
        result.append(" ] }'");

        return result.toString();
    }

    private String advertsToJSON(StringBuffer result, AdvertVO advert, AdvertStandardNodeVO advertStandardNode)
    {
        result.append("{ ");
        if (advertStandardNode.getName() != null)
            result.append("\"name\": \"" + advertStandardNode.getName() + "\", ");
        if (advertStandardNode.getSummary() != null)
            result.append("\"summary\": \"" + advertStandardNode.getSummary() + "\", ");
        if (advertStandardNode.getDiscription() != null)
            result.append("\"discription\": \"" + advertStandardNode.getDiscription() + "\", ");
        result.append("\"children\": [");
        boolean firstChild = true;
        for (AdvertNodeVO childNode: advertStandardNode.getChildNodes())
        {
            if (firstChild)
                firstChild = false;
            else
                result.append(", ");
            advertsToJSON(result, advert, (AdvertStandardNodeVO) childNode);
        }
        result.append(" ]");
        result.append(" }");

        return result.toString();
    }

    private List<String>   _serviceRootURLs;
    private List<String>   _requesterIds;
    private String         _userId;
    private String         _advertsJSON;
    private List<AdvertVO> _adverts;
    private String         _errorMessage;

    @EJB
    private AdvertClient _advertClient;
}
