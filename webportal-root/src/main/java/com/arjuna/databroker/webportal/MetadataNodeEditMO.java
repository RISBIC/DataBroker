/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.MetadataClient;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

@SessionScoped
@ManagedBean(name="metadatanodeedit")
public class MetadataNodeEditMO implements Serializable
{
    private static final long serialVersionUID = -3603881327332151804L;

    private static final Logger logger = Logger.getLogger(MetadataNodeEditMO.class.getName());

    public MetadataNodeEditMO()
    {
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getSummary()
    {
        return _summary;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public String getDetails()
    {
        return _details;
    }

    public void setDetails(String details)
    {
        _details = details;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        _errorMessage = errorMessage;
    }

    public String getServiceRootURL()
    {
        return _serviceRootURL;
    }

    public void setServiceRootURL(String serviceRootURL)
    {
        _serviceRootURL = serviceRootURL;
    }

    public String getRequesterId()
    {
        return _requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        _requesterId = requesterId;
    }

    public String getUserId()
    {
        return _userId;
    }

    public void setUserId(String userId)
    {
        _userId = userId;
    }

    public String getMetadataId()
    {
        return _metadataId;
    }

    public void setMetadataId(String metadataId)
    {
        _metadataId = metadataId;
    }

    public String getResourceURI()
    {
        return _resourceURI;
    }

    public void setResourceURI(String resourceURI)
    {
        _resourceURI = resourceURI;
    }

    public String doEdit(String serviceRootURL, String requesterId, String userId, String metadataId, String resourceURI, String title, String summary, String details)
    {
        _serviceRootURL = serviceRootURL;
        _requesterId    = requesterId;
        _userId         = userId;
        _metadataId     = metadataId;
        _resourceURI    = resourceURI;

        _errorMessage = null;

        _title   = title;
        _summary = summary;
        _details = details;

        return "/dataviews/metadatanode_edit?faces-redirect=true";
    }

    public String doChangeSubmit()
    {
        try
        {
            String content = _metadataClient.getContent(_serviceRootURL, _requesterId, _userId, _metadataId);

            if (content != null)
            {
                Model  model  = ModelFactory.createDefaultModel();
                Reader reader = new StringReader(content);
                model.read(reader, null);
                reader.close();

                Property hasTitle   = model.getProperty("http://rdfs.arjuna.com/description#", "hasTitle");
                Property hasSummary = model.getProperty("http://rdfs.arjuna.com/description#", "hasSummary");
                Property hasDetails = model.getProperty("http://rdfs.arjuna.com/description#", "hasDetails");

                Resource resource = model.createResource(_resourceURI);
                resource.removeAll(hasTitle);
                resource.removeAll(hasSummary);
                resource.removeAll(hasDetails);

                if (_title != null)
                    resource.addProperty(hasTitle, _title.trim());
                if ((_summary != null))
                    resource.addProperty(hasSummary, _summary.trim());
                if ((_details != null))
                    resource.addProperty(hasDetails, _details.trim());

                StringWriter writer = new StringWriter();
                model.write(writer);
                model.close();
                content = writer.toString();

                if (! _metadataClient.setContent(_serviceRootURL, _requesterId, _userId, _metadataId, content))
                    _errorMessage = "Problem to saving data view to DataBroker";
                else
                    _errorMessage = null;
            }
            else
                _errorMessage = "Unable to load data view from DataBroker";
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem generating Tree", throwable);
            _errorMessage = "Problem occured while generating presentation of metadata";
        }
        
        return "/dataviews/metadatanode_edit_done?faces-redirect=true";
    }

    private String _title;
    private String _summary;
    private String _details;

    private String _errorMessage;

    private String _serviceRootURL;
    private String _requesterId;
    private String _userId;
    private String _metadataId;
    private String _resourceURI;

    @EJB
    private MetadataClient _metadataClient;
}
