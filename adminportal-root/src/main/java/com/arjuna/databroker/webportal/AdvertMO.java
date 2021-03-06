/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
        _serviceRootURLs = Collections.emptyList();
        _requesterIds    = Collections.emptyList();
        _userId          = null;
        _adverts         = new LinkedList<AdvertVO>();
        _advertsJSON     = "'[ ]'";
        _advertTreeJSON  = "'{ \"children\": [ ] }'";

        _serverStatusMessages = new HashMap<String, String>();
        _errorMessage         = null;
        _serverErrorMessages  = new HashMap<String, String>();

        _loadWorker = new LoadWorker();
        _syncObject = new Object();
    }

    @PostConstruct
    private void startupWorker()
    {
        logger.log(Level.FINE, "AdvertMO.startupWorker");

        _loadWorker.start();
    }

    @PreDestroy
    private void shutdownWorker()
    {
        logger.log(Level.FINE, "AdvertMO.shutdownWorker");

        try
        {
            _loadWorker.stopWorking();
            _loadWorker.finish();
            _loadWorker.join();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "AdvertMO.shutdownWorker", throwable);
        }
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

    public String getAdvertTreeJSON()
    {
        return _advertTreeJSON;
    }

    public void setAdvertTreeJSON(String advertTreeJSON)
    {
        _advertTreeJSON = advertTreeJSON;
    }

    public Map<String, String> getServerStatusMessages()
    {
        return _serverStatusMessages;
    }

    public void setServerStatusMessages(Map<String, String> serverStatusMessages)
    {
        _serverStatusMessages = serverStatusMessages;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public Map<String, String> getServerErrorMessages()
    {
        return _serverErrorMessages;
    }

    public void setServerErrorMessages(Map<String, String> serverErrorMessages)
    {
        _serverErrorMessages = serverErrorMessages;
    }

    public Boolean getAsyncLoadInProgress()
    {
        return _loadWorker.getAsyncLoadInProgress();
    }

    public String doLoad(String serviceRootURL, String requesterId, String userId)
    {
        synchronized (_syncObject)
        {
            logger.log(Level.FINE, "AdvertMO.doLoad: " + serviceRootURL + ", " + requesterId + ", " + userId);

            _loadWorker.stopWorking();

            _serviceRootURLs = new LinkedList<String>();
            _requesterIds    = new LinkedList<String>();
            Collections.addAll(_serviceRootURLs, serviceRootURL);
            Collections.addAll(_requesterIds, requesterId);
            _userId = userId;

            load();

            return "/dataviews/dataadvert_tree?faces-redirect=true";
        }
    }

    public String doLoad(List<String> serviceRootURLs, List<String> requesterIds, String userId)
    {
        synchronized (_syncObject)
        {
            logger.log(Level.FINE, "AdvertMO.doLoad: " + serviceRootURLs + ", " + requesterIds + ", " + userId);

            _loadWorker.stopWorking();

            _serviceRootURLs = serviceRootURLs;
            _requesterIds    = requesterIds;
            _userId          = userId;

            load();

            return "/dataviews/dataadvert_tree?faces-redirect=true";
        }
    }

    public String doReload()
    {
        synchronized (_syncObject)
        {
            logger.log(Level.FINE, "AdvertMO.doReload: " + _serviceRootURLs + ", " + _requesterIds + ", " + _userId);

            _loadWorker.stopWorking();

            load();

            return "#";
        }
    }

    public String doToTreeTab()
    {
        return "/dataviews/dataadvert_tree?faces-redirect=true";
    }

    public String doToSearchTab()
    {
        return "/dataviews/dataadvert_search?faces-redirect=true";
    }

    public String doAsyncLoad(String serviceRootURL, String requesterId, String userId)
    {
        synchronized (_syncObject)
        {
            logger.log(Level.FINE, "AdvertMO.doAsyncLoad: " + serviceRootURL + ", " + requesterId + ", " + userId);

            _loadWorker.stopWorking();

            _serviceRootURLs = new LinkedList<String>();
            _requesterIds    = new LinkedList<String>();
            Collections.addAll(_serviceRootURLs, serviceRootURL);
            Collections.addAll(_requesterIds, requesterId);
            _userId = userId;

            asyncLoad();

            return "/dataviews/dataadvert_tree?faces-redirect=true";
        }
    }

    public String doAsyncLoad(List<String> serviceRootURLs, List<String> requesterIds, String userId)
    {
        synchronized (_syncObject)
        {
            logger.log(Level.FINE, "AdvertMO.doAsyncLoad: " + serviceRootURLs + ", " + requesterIds + ", " + userId);

            _loadWorker.stopWorking();

            _serviceRootURLs = serviceRootURLs;
            _requesterIds    = requesterIds;
            _userId          = userId;

            asyncLoad();

            return "/dataviews/dataadvert_tree?faces-redirect=true";
        }
    }

    public String doAsyncReload()
    {
        synchronized (_syncObject)
        {
            logger.log(Level.FINE, "AdvertMO.doAsyncReload: " + _serviceRootURLs + ", " + _requesterIds + ", " + _userId);

            _loadWorker.stopWorking();

            asyncLoad();

            return "/dataviews/dataadvert?faces-redirect=true";
        }
    }

    public String doRefresh()
    {
        return "/dataviews/dataadvert?faces-redirect=true";
    }

    private void load()
    {
        logger.log(Level.FINE, "AdvertMO.load");

        try
        {
            _adverts.clear();
            _advertsJSON     = "'[ ]'";
            _advertTreeJSON  = "'{ \"children\": [ ] }'";
            _serverStatusMessages.clear();
            _errorMessage = null;
            _serverErrorMessages.clear();
            if ((_serviceRootURLs == null) || (_requesterIds == null))
                _errorMessage = "Null 'service root URLs' or 'requester ids'";
            else if (_serviceRootURLs.size() != _requesterIds.size())
                _errorMessage = "Size mismatch between 'service root URLs' or 'requester ids'";
            else
            {
                for (int index = 0; index < _serviceRootURLs.size(); index++)
                    _serverStatusMessages.put(_serviceRootURLs.get(index), "Scheduled");

                for (int index = 0; index < _serviceRootURLs.size(); index++)
                {
                    String serviceRootURL = _serviceRootURLs.get(index);
                    String requesterIds   = _requesterIds.get(index);

                    _serverStatusMessages.put(serviceRootURL, "Ongoing");

                    try
                    {
                        obtainAdverts(serviceRootURL, requesterIds, _userId);

                        _serverStatusMessages.put(serviceRootURL, "Complete");
                    }
                    catch (Throwable throwable)
                    {
                        _serverStatusMessages.put(serviceRootURL, "Failed");
                    }
                }

                _advertsJSON    = advertsToJSON(_adverts);
                _advertTreeJSON = advertsToTreeJSON(_adverts);
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem getting metadata ids", throwable);
            _adverts.clear();
            _errorMessage = "Problem getting metadata ids";
        }
    }

    private void asyncLoad()
    {
        logger.log(Level.FINE, "AdvertMO.asyncLoad");

        try
        {
            _adverts.clear();
            _advertTreeJSON  = "'{ \"children\": [ ] }'";
            _serverStatusMessages.clear();
            _errorMessage = null;
            _serverErrorMessages.clear();
            if ((_serviceRootURLs == null) || (_requesterIds == null))
                _errorMessage = "Null 'service root URLs' or 'requester ids'";
            else if (_serviceRootURLs.size() != _requesterIds.size())
                _errorMessage = "Size mismatch between 'service root URLs' or 'requester ids'";
            else
            {
                _loadWorker.stopWorking();

                for (int index = 0; index < _serviceRootURLs.size(); index++)
                    _serverStatusMessages.put(_serviceRootURLs.get(index), "Scheduled");

                _loadWorker.restartWorking(_serviceRootURLs, _requesterIds, _userId);
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem getting metadata ids", throwable);
            _adverts.clear();
            _errorMessage = "Problem getting metadata ids";
        }
    }

    private class LoadWorker extends Thread
    {
        public LoadWorker()
        {
            _asyncLoadInProgress = false;
            _startWaitObject     = new Object();
            _workToDo            = false;
            _finish              = false;
        }

        public Boolean getAsyncLoadInProgress()
        {
            return _asyncLoadInProgress;
        }

        public void restartWorking(List<String> serviceRootURLs, List<String> requesterIds, String userId)
        {
            _workServiceRootURLs = serviceRootURLs;
            _workRequesterIds    = requesterIds;
            _workUserId          = userId;

            if ((_workServiceRootURLs == null) || (_workRequesterIds == null))
                _errorMessage = "Null 'service root URLs' or 'requester ids'";
            else if (_workServiceRootURLs.size() != _workRequesterIds.size())
                _errorMessage = "Size mismatch between 'service root URLs' or 'requester ids'";
            else
            {
                synchronized (_startWaitObject)
                {
                    _workToDo = true;
                    _startWaitObject.notify();
                }
            }
        }

        public void stopWorking()
        {
            synchronized (_startWaitObject)
            {
                _workToDo = false;
                this.interrupt();
            }
        }

        public void finish()
        {
            synchronized (_startWaitObject)
            {
                _workToDo = false;
                _finish   = true;
                this.interrupt();
            }
        }

        public void run()
        {
            while (! _finish)
            {
                try
                {
                    synchronized (_startWaitObject)
                    {
                        if (! _workToDo)
                            _startWaitObject.wait();
                    }

                    _asyncLoadInProgress = true;

                    for (int index = 0; _workToDo && (index < _workServiceRootURLs.size()); index++)
                    {
                        String serviceRootURL = _workServiceRootURLs.get(index);
                        String requesterIds   = _workRequesterIds.get(index);

                        _serverStatusMessages.put(serviceRootURL, "Ongoing");

                        try
                        {
                            obtainAdverts(serviceRootURL, requesterIds, _workUserId);

                            _serverStatusMessages.put(serviceRootURL, "Complete");
                        }
                        catch (Throwable throwable)
                        {
                            _serverStatusMessages.put(serviceRootURL, "Failed");
                        }

                        _advertsJSON    = advertsToJSON(_adverts);
                        _advertTreeJSON = advertsToTreeJSON(_adverts);
                    }
                }
                catch (InterruptedException interruptedException)
                {
                }
                catch (Throwable throwable)
                {
                    logger.log(Level.WARNING, "Problem getting adverts", throwable);
                }

                _workToDo            = false;
                _asyncLoadInProgress = false;
            }
        }

        private List<String> _workServiceRootURLs;
        private List<String> _workRequesterIds;
        private String       _workUserId;

        private volatile boolean _asyncLoadInProgress;
        private volatile boolean _workToDo;
        private final    Object  _startWaitObject;
        private volatile boolean _finish;
    }

    private void obtainAdverts(String serviceRootURL, String requesterId, String userId)
    {
        List<AdvertNodeSummary> advertNodeSummaries = _advertClient.getAdverts(serviceRootURL, requesterId, userId);

        if (advertNodeSummaries != null)
        {
            Map<String, AdvertNodeVO> advertNodeMap = new HashMap<String, AdvertNodeVO>();
            for (AdvertNodeSummary advertNodeSummary: advertNodeSummaries)
                advertNodeMap.put(advertNodeSummary.getId(), new AdvertStandardNodeVO(advertNodeSummary.getNodeClass(), advertNodeSummary.getName(), advertNodeSummary.getSummary(), advertNodeSummary.getDescription(), advertNodeSummary.getDateCreated(), advertNodeSummary.getDateUpdate(), advertNodeSummary.getOwner(), advertNodeSummary.getTags(), null));

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

    private String advertsToJSON(List<AdvertVO> adverts)
    {
        StringBuffer result = new StringBuffer();

        result.append("'[ ");
        boolean firstAdvert = true;
        for (AdvertVO advert: _adverts)
        {
            if (firstAdvert)
                firstAdvert = false;
            else
                result.append(", ");
            advertToJSON(result, advert, (AdvertStandardNodeVO) advert.getNode());
        }
        result.append(" ]'");

        return result.toString();
    }

    private String advertToJSON(StringBuffer result, AdvertVO advert, AdvertStandardNodeVO advertStandardNode)
    {
        String id          = advert.getMetadataId();
        String path        = advert.getMetadataPath();
        String name        = advertStandardNode.getName();
        String summary     = advertStandardNode.getSummary();
        String description = advertStandardNode.getDescription();

        if ("".equals(name))
            name = null;
        if ("".equals(summary))
            summary = null;
        if ("".equals(description))
            description = null;

        if ((name != null) || (summary != null) || (description != null))
        {
            result.append("{ ");
            result.append("\"id\": \"" + id + "\"");
            result.append(", \"path\": \"" + path + "\"");
            if (name != null)
                result.append(", \"name\": \"" + name + "\"");
            if (summary != null)
                result.append(", \"summary\": \"" + summary + "\"");
            if (description != null)
                result.append(", \"description\": \"" + description + "\"");
            result.append(" }");
        }

        return result.toString();
    }

    private String advertsToTreeJSON(List<AdvertVO> adverts)
    {
        StringBuffer result = new StringBuffer();

        result.append("'{ \"children\": [ ");
        boolean firstAdvert = true;
        for (AdvertVO advert: _adverts)
            if (advert.getIsRootNode())
            {
                if (firstAdvert)
                    firstAdvert = false;
                else
                    result.append(", ");
                advertsToTreeJSON(result, advert, (AdvertStandardNodeVO) advert.getNode());
            }
        result.append(" ] }'");

        return result.toString();
    }

    private String advertsToTreeJSON(StringBuffer result, AdvertVO advert, AdvertStandardNodeVO advertStandardNode)
    {
        result.append("{ ");
        if (advertStandardNode.getName() != null)
            result.append("\"name\": \"" + advertStandardNode.getName() + "\", ");
        if (advertStandardNode.getSummary() != null)
            result.append("\"summary\": \"" + advertStandardNode.getSummary() + "\", ");
        if (advertStandardNode.getDescription() != null)
            result.append("\"description\": \"" + advertStandardNode.getDescription() + "\", ");
        result.append("\"children\": [");
        boolean firstChild = true;
        for (AdvertNodeVO childNode: advertStandardNode.getChildNodes())
        {
            if (firstChild)
                firstChild = false;
            else
                result.append(", ");
            advertsToTreeJSON(result, advert, (AdvertStandardNodeVO) childNode);
        }
        result.append(" ]");
        result.append(" }");

        return result.toString();
    }

    private List<String>   _serviceRootURLs;
    private List<String>   _requesterIds;
    private String         _userId;
    private List<AdvertVO> _adverts;
    private String         _advertsJSON;
    private String         _advertTreeJSON;

    private Map<String, String> _serverStatusMessages;
    private String              _errorMessage;
    private Map<String, String> _serverErrorMessages;

    private LoadWorker     _loadWorker;
    private Object         _syncObject;

    @EJB
    private AdvertClient _advertClient;
}
