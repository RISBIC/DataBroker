/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import com.arjuna.databroker.webportal.tree.AbstractTreeNode;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

@SessionScoped
@ManagedBean(name="metadatanode")
public class MetadataNodeMO implements Serializable
{
    private static final long serialVersionUID = 6889325827477797965L;

    private static final Logger logger = Logger.getLogger(MetadataNodeMO.class.getName());

    public MetadataNodeMO()
    {
    }

    public String getName()
    {
        return _name;
    }

    public String getResourceURI()
    {
        return _resourceURI;
    }

    public String getTitle()
    {
        return _title;
    }

    public String getSummary()
    {
        return _summary;
    }

    public String getDetails()
    {
        return _details;
    }

    public String getOwner()
    {
        return _owner;
    }

    public String getTags()
    {
        return _tags;
    }

    public String getLocation()
    {
        return _location;
    }

    public void clearSelection()
    {
        _name        = "";
        _resourceURI = "";
        _title       = "";
        _summary     = "";
        _details     = ""; 
        _owner       = "";
        _tags        = "";
        _location    = "";
   }

    public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent)
    {
        List<Object> selection    = new ArrayList<Object>(selectionChangeEvent.getNewSelection());
        Object       selectionKey = selection.get(0);
        UITree       tree         = (UITree) selectionChangeEvent.getSource();

        Object key = tree.getRowKey();
        tree.setRowKey(selectionKey);

        try
        {
            AbstractTreeNode abstractTreeNode = (AbstractTreeNode) tree.getRowData();
            Model            model            = abstractTreeNode.getModel();
            Resource         resource         = abstractTreeNode.getResource();

            Property  hasTitle          = model.getProperty("http://rdfs.arjuna.com/description#", "hasTitle");
            Property  hasSummary        = model.getProperty("http://rdfs.arjuna.com/description#", "hasSummary");
            Property  hasDetails        = model.getProperty("http://rdfs.arjuna.com/description#", "hasDetails");
            Property  hasOwner          = model.getProperty("http://rdfs.arjuna.com/description#", "hasOwner");
            Property  hasTag            = model.getProperty("http://rdfs.arjuna.com/description#", "hasTag");
            Property  hasLocation       = model.getProperty("http://rdfs.arjuna.com/description#", "hasLocation");
            Statement titleStatement    = resource.getProperty(hasTitle);
            Statement summaryStatement  = resource.getProperty(hasSummary);
            Statement detailsStatement  = resource.getProperty(hasDetails);
            Statement ownerStatement    = resource.getProperty(hasOwner);
            Statement tagStatement      = resource.getProperty(hasTag);
            Statement locationStatement = resource.getProperty(hasLocation);

            _name = abstractTreeNode.getName();

            _resourceURI = resource.getURI();

            if (titleStatement != null)
                _title = titleStatement.getString();
            else
                _title = "";

            if (summaryStatement != null)
                _summary = summaryStatement.getString();
            else
                _summary = "";

            if (detailsStatement != null)
                _details = detailsStatement.getString();
            else
                _details = "";

            if (ownerStatement != null)
                _owner = ownerStatement.getString();
            else
                _owner = "";

            // TODO: Tags
	    
            if (locationStatement != null)
                _location = locationStatement.getString();
            else
                _location = "";
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem while processing rdf", throwable);
            _name        = "";
            _resourceURI = "";
            _title       = "";
            _summary     = "";
            _details     = "";
            _owner       = "";
            _tags        = "";
            _location    = "";
        }

        tree.setRowKey(key);
    }

    private String _name;
    private String _resourceURI;
    private String _title;
    private String _summary;
    private String _details;
    private String _owner;
    private String _tags;
    private String _location;
}
