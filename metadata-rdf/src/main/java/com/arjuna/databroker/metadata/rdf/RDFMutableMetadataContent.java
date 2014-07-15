/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.Proxy;
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.invocationhandlers.MutableMetadataContentViewInvocationHandler;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class RDFMutableMetadataContent extends RDFMetadataContent implements MutableMetadataContent
{
    public RDFMutableMetadataContent(Resource resource)
    {
        super(resource);
    }

    @Override
    public <T> void addMetadataStatement(String name, T value)
    {
        Model    model    = _resource.getModel();
        Property property = model.getProperty(name);

        _resource.removeAll(property);
        _resource.addProperty(property, value.toString());
    }

    @Override
    public <T> void removeMetadataStatement(String name)
    {
        Model    model    = _resource.getModel();
        Property property = model.getProperty(name);

        _resource.removeAll(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V getView(Class<V> viewInterface)
        throws IllegalArgumentException
    {
        if (! viewInterface.isInterface())
            throw new IllegalArgumentException("View Interface is not an interface");
        else if (! viewInterface.isAnnotationPresent(MetadataContentView.class))
            throw new IllegalArgumentException("View Interface is not annotated as a MetadataContentView");
        else
            return (V) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { viewInterface }, new MutableMetadataContentViewInvocationHandler(this));
    }
}