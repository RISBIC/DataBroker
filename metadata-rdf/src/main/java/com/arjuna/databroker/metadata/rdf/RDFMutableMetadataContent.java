/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.Proxy;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.annotations.MutableMetadataView;
import com.arjuna.databroker.metadata.invocationhandlers.MutableMetadataContentViewInvocationHandler;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMutableMetadataContentSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMutableMetadataStatementSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMutableMetadataStatementsSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MutableMetadataStatementsSelector;

public class RDFMutableMetadataContent extends RDFMetadataContent implements MutableMetadataContent
{
    public RDFMutableMetadataContent(Resource resource)
    {
        super(resource);
    }

    @Override
    public MutableMetadataStatementSelector mutableStatement(String name, String type)
    {
        if (_resource != null)
        {
            Model    model    = _resource.getModel();
            Property property = model.getProperty(name);

            return new RDFMutableMetadataStatementSelector(_resource.getProperty(property));
        }
        else
            return new RDFMutableMetadataStatementSelector(null);
    }

    @Override
    public MutableMetadataStatementsSelector mutableStatements()
    {
        return new RDFMutableMetadataStatementsSelector(_resource);
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
    public <S extends MutableMetadataContentSelector> S mutableSelector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMutableMetadataContentSelector.class))
            return (S) new RDFMutableMetadataContentSelector(_resource);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V getView(Class<V> viewInterface)
        throws IllegalArgumentException
    {
        if (! viewInterface.isInterface())
            throw new IllegalArgumentException("View Interface is not an interface");
        else if (! viewInterface.isAnnotationPresent(MutableMetadataView.class))
            throw new IllegalArgumentException("View Interface is not annotated as a MutableMetadataView");
        else
            return (V) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { viewInterface }, new MutableMetadataContentViewInvocationHandler(this));
    }
}