/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import com.arjuna.databroker.webportal.comms.MetadataClient;
import com.arjuna.databroker.webportal.tree.AbstractTreeNode;
import com.arjuna.databroker.webportal.tree.DataSourceTreeNode;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@SessionScoped
@ManagedBean(name="metadatalist")
public class MetadataListMO implements Serializable
{
    private static final long serialVersionUID = 8178179989936428283L;

    private static final Logger logger = Logger.getLogger(MetadataListMO.class.getName());

    public MetadataListMO()
    {
        _items        = new LinkedList<MetadataItemVO>();
        _errorMessage = null;
    }

    public List<MetadataItemVO> getItems()
    {
        return _items;
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

    public String doLoad(String serviceRootURL, String requesterId, String userId, String metadataId)
    {
        logger.log(Level.FINE, "MetadataListMO.doLoad: " + serviceRootURL + ", " + requesterId + ", " + userId + ", " + metadataId);

        _serviceRootURL = serviceRootURL;
        _requesterId    = requesterId;
        _userId         = userId;
        _metadataId     = metadataId;

        load();

        return "metadatalist?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "MetadataListMO.doReload: " + _serviceRootURL + ", " + _requesterId + ", " + _userId + ", " + _metadataId);

        load();

        return "metadatalist?faces-redirect=true";
    }

    private void load()
    {
        logger.log(Level.FINE, "MetadataListMO.load");
        try
        {
            String content = _metadataClient.getContent(_serviceRootURL, _requesterId, _userId, _metadataId);

            if (content != null)
            {
                _model = ModelFactory.createDefaultModel();
                Reader reader = new StringReader(content);
                _model.read(reader, null);
                reader.close();

                Property rdfTypeProperty    = _model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type");
                Property dataSourceProperty = _model.getProperty("http://rdfs.arjuna.com/datasource#", "DataSource");

                StmtIterator statements = _model.listStatements(null, rdfTypeProperty, dataSourceProperty);

                _items.clear();
                while (statements.hasNext())
                {
                    Statement statement = statements.nextStatement();
                    _items.add(new MetadataItemVO("Name", "Value", new LinkedList<MetadataItemVO>()));
                }
                _errorMessage = null;
            }
            else
            {
                _items.clear();
                _errorMessage = "Unable to load data view form DataBroker";
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem generating Tree", throwable);
            _items.clear();
            _errorMessage = "Problem occured while generating presentation of metadata";
        }
    }

    private Model _model;

    private List<MetadataItemVO> _items;
    private String               _errorMessage;
    private String               _serviceRootURL;
    private String               _requesterId;
    private String               _userId;
    private String               _metadataId;

    @EJB
    private MetadataClient _metadataClient;
}
