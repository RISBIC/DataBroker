/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.Proxy;
import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.invocationhandlers.MetadataContentViewInvocationHandler;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataStatementSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataStatementsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class RDFMetadataContent implements MetadataContent
{
    public RDFMetadataContent(Resource resource)
    {
        _resource = resource;
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
            return (V) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { viewInterface }, new MetadataContentViewInvocationHandler(this));
    }

    @Override
    public MetadataStatementSelector statement(String name, String type)
    {
        if (_resource != null)
        {
            Model    model    = _resource.getModel();
            Property property = model.getProperty(name);

            return new RDFMetadataStatementSelector(_resource.getProperty(property));
        }
        else
            return new RDFMetadataStatementSelector(null);
    }

    @Override
    public MetadataStatementsSelector statements()
    {
        return new RDFMetadataStatementsSelector(_resource);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadataContent> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(RDFMutableMetadataContent.class))
            return (M) new RDFMutableMetadataContent(_resource);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataContentSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataContentSelector.class))
            return (S) new RDFMetadataContentSelector(_resource);
        else
            return null;
    }

    protected Resource _resource;
}