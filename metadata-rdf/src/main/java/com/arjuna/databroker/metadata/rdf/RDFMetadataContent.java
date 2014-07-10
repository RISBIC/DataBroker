/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.Proxy;
import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.annotations.MetadataContentView;
import com.arjuna.databroker.metadata.invocationhandlers.MetadataContentViewInvocationHandler;
import com.arjuna.databroker.metadata.selectors.MetadataContentSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementsSelector;

public class RDFMetadataContent implements MetadataContent
{
    public RDFMetadataContent()
    {
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
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MetadataStatementsSelector statements()
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadataContent> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(RDFMetadataContent.class))
            return (M) new RDFMutableMetadataContent();
        else
            return null;
    }
    
    @Override
    public <S extends MetadataContentSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}