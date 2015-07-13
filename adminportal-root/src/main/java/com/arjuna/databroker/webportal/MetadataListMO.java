/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.comms.MetadataClient;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
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

        return "/dataviews/metadatalist?faces-redirect=true";
    }

    public String doReload()
    {
        logger.log(Level.FINE, "MetadataListMO.doReload: " + _serviceRootURL + ", " + _requesterId + ", " + _userId + ", " + _metadataId);

        load();

        return "#";
    }

    private void load()
    {
        logger.log(Level.FINE, "MetadataListMO.load");
        try
        {
            synchronized (_items)
            {
                String content = _metadataClient.getContent(_serviceRootURL, _requesterId, _userId, _metadataId);

                if (content != null)
                {
                    Model model = ModelFactory.createDefaultModel();
                    Reader reader = new StringReader(content);
                    model.read(reader, null);
                    reader.close();

                    Property rdfTypeProperty    = model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type");
                    Property dataSourceProperty = model.getProperty("http://rdfs.arjuna.com/datasource#", "DataSource");
                    Property workbookProperty   = model.getProperty("http://rdfs.arjuna.com/xssf#", "Workbook");
                    Property jsonDocProperty    = model.getProperty("http://rdfs.arjuna.com/json#", "Document");

                    _items.clear();

                    StmtIterator dataSourceStatements = model.listStatements(null, rdfTypeProperty, dataSourceProperty);
                    while (dataSourceStatements.hasNext())
                    {
                        MetadataItemVO item = buildItem(model, dataSourceStatements.nextStatement().getSubject(), "Data Source");

                        if (item != null)
                            _items.add(item);
                    }
                    StmtIterator workbookStatements = model.listStatements(null, rdfTypeProperty, workbookProperty);
                    while (workbookStatements.hasNext())
                    {
                        MetadataItemVO item = buildItem(model, workbookStatements.nextStatement().getSubject(), "Workbook");

                        if (item != null)
                            _items.add(item);
                    }
                    StmtIterator jsonDocStatements = model.listStatements(null, rdfTypeProperty, jsonDocProperty);
                    while (jsonDocStatements.hasNext())
                    {
                        MetadataItemVO item = buildItem(model, jsonDocStatements.nextStatement().getSubject(), "Document");

                        if (item != null)
                            _items.add(item);
                    }
                    _errorMessage = null;
                }
                else
                {
                    _items.clear();
                    _errorMessage = "Unable to load data view form DataBroker";
                }
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem generating Tree", throwable);
            _items.clear();
            _errorMessage = "Problem occured while generating presentation of metadata";
        }
    }

    private MetadataItemVO buildItem(Model model, Resource resource, String type)
    {
        List<MetadataItemVO> items   = new LinkedList<MetadataItemVO>();

        Property hasTitle        = model.getProperty("http://rdfs.arjuna.com/description#", "hasTitle");
        Property hasSummary      = model.getProperty("http://rdfs.arjuna.com/description#", "hasSummary");
        Property hasDetails      = model.getProperty("http://rdfs.arjuna.com/description#", "hasDetails");
        Property hasAccessInfo   = model.getProperty("http://rdfs.arjuna.com/access#", "hasAccessInfo");
        Property useUsername     = model.getProperty("http://rdfs.arjuna.com/access#", "useUsername");
        Property usePassword     = model.getProperty("http://rdfs.arjuna.com/access#", "usePassword");
        Property useServiceURL   = model.getProperty("http://rdfs.arjuna.com/access#", "useServiceURL");
        Property useProtocol     = model.getProperty("http://rdfs.arjuna.com/access#", "useProtocol");
        Property hasDataService  = model.getProperty("http://rdfs.arjuna.com/datasource#", "hasDataService");
        Property producesDataSet = model.getProperty("http://rdfs.arjuna.com/datasource#", "producesDataSet");
        Property hasField        = model.getProperty("http://rdfs.arjuna.com/datasource#", "hasField");
        Property hasType         = model.getProperty("http://rdfs.arjuna.com/datasource#", "hasType");
        Property hasSheet        = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasSheet");
        Property hasColumn       = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasColumn");
        Property hasLabel        = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasLabel");
        Property hasIndex        = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasIndex");
        Property hasXType        = model.getProperty("http://rdfs.arjuna.com/xssf#", "hasType");
        Property hasJSONField    = model.getProperty("http://rdfs.arjuna.com/json#", "hasField");

        Statement summaryStatement = resource.getProperty(hasSummary);
        if (summaryStatement != null)
            items.add(new MetadataItemVO("Summary: ", summaryStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement detailsStatement = resource.getProperty(hasDetails);
        if (detailsStatement != null)
            items.add(new MetadataItemVO("Details: ", detailsStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement usernameStatement = resource.getProperty(useUsername);
        if (usernameStatement != null)
            items.add(new MetadataItemVO("Username: ", usernameStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement passwordStatement = resource.getProperty(usePassword);
        if (passwordStatement != null)
            items.add(new MetadataItemVO("Password: ", passwordStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement serviceURLStatement = resource.getProperty(useServiceURL);
        if (serviceURLStatement != null)
            items.add(new MetadataItemVO("Service URL: ", serviceURLStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement protocolStatement = resource.getProperty(useProtocol);
        if (protocolStatement != null)
            items.add(new MetadataItemVO("Protocol: ", protocolStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement labelStatement = resource.getProperty(hasLabel);
        if (labelStatement != null)
            items.add(new MetadataItemVO("Label: ", labelStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement indexStatement = resource.getProperty(hasIndex);
        if (indexStatement != null)
            items.add(new MetadataItemVO("Index: ", indexStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement xtypeStatement = resource.getProperty(hasXType);
        if (xtypeStatement != null)
            items.add(new MetadataItemVO("Type: ", xtypeStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        Statement hasJSONFieldStatement = resource.getProperty(hasJSONField);
        if (hasJSONFieldStatement != null)
            items.add(new MetadataItemVO("JSON Field: ", hasJSONFieldStatement.getString(), Collections.<MetadataItemVO>emptyList()));

        StmtIterator statements = model.listStatements(resource, (Property) null, (RDFNode) null);
        while (statements.hasNext())
        {
            Statement subStatement = statements.nextStatement();

            MetadataItemVO subItem = null;
            if (hasDataService.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Data Service");
            else if (hasAccessInfo.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Access");
            else if (producesDataSet.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Data Set");
            else if (hasField.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Data Field");
            else if (hasType.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Data Type");
            else if (hasSheet.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Sheet");
            else if (hasColumn.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "Column");
            else if (hasJSONField.equals(subStatement.getPredicate()))
                subItem = buildItem(model, subStatement.getResource(), "JSON Field");

            if (subItem != null)
                items.add(subItem);
        }

        Statement titleStatement = resource.getProperty(hasTitle);
        if (titleStatement != null)
            return new MetadataItemVO(type + " - ", titleStatement.getString(), items);
        else if (! items.isEmpty())
            return new MetadataItemVO(type + " - unknown", null, items);
        else
            return null;
    }

    private List<MetadataItemVO> _items;
    private String               _errorMessage;
    private String               _serviceRootURL;
    private String               _requesterId;
    private String               _userId;
    private String               _metadataId;

    @EJB
    private MetadataClient _metadataClient;
}
