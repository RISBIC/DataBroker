/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf.selectors;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.arjuna.databroker.metadata.selectors.MutableMetadataContentsSelector;

public class RDFMutableMetadataContentsSelector extends RDFMetadataContentsSelector implements MutableMetadataContentsSelector
{
    public RDFMutableMetadataContentsSelector(Model model)
    {
        super(model);
    }

    public RDFMutableMetadataContentSelector mutableWithPath(String path)
    {
        return new RDFMutableMetadataContentSelector(_model.getResource(path));
    }

    public RDFMutableMetadataContentSelector mutableWithPropertyValue(String propertyName, String propertyValue)
    {
        if (_model != null)
        {
            Property    property         = _model.getProperty(propertyName);
            ResIterator resourceIterator = _model.listResourcesWithProperty(property, propertyValue);

            return new RDFMutableMetadataContentSelector(resourceIterator.nextResource());
        }
        else
            return new RDFMutableMetadataContentSelector(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MutableMetadataContentsSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMutableMetadataContentsSelector.class))
            return (S) this;
        else
            return null;
    }
}