/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.arjuna.databroker.metadata.MetadataStatement;
import com.arjuna.databroker.metadata.MutableMetadataStatement;
import com.arjuna.databroker.metadata.annotations.MetadataView;
import com.arjuna.databroker.metadata.invocationhandlers.MutableMetadataContentViewInvocationHandler;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFMetadataStatement implements MetadataStatement
{
    public RDFMetadataStatement(Statement statement)
    {
        _statement = statement;
    }

    @Override
    public String getName()
    {
        return _statement.getSubject().getURI();
    }

    @Override
    public String getType()
    {
        return _statement.getPredicate().getURI();
    }

    @Override
    public <T> T getValue(Class<T> valueClass)
    {
        if (_statement != null)
            return getValue(_statement.getLiteral(), valueClass);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Type valueType)
    {
        if (_statement != null)
        {
            if (valueType instanceof Class<?>)
                return getValue((Class<T>) valueType);
            else if (valueType instanceof ParameterizedType)
            {
                ParameterizedType parameterizedType = (ParameterizedType) valueType;

                if (parameterizedType.getRawType().equals(List.class) && (parameterizedType.getActualTypeArguments().length == 1))
                {
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

                    List<Object> list        = new LinkedList<Object>();
                    NodeIterator bagIterator = _statement.getBag().iterator();
                    while (bagIterator.hasNext())
                        list.add(getValue(bagIterator.next(), (Class<?>) actualTypeArgument));

                    return (T) list;
                }

                return null;
            }
            else
                return null;
        }
        else
            return null;
    }

    @Override
    public MetadataStatementSelector statement()
    {
        return new RDFMetadataStatementSelector(_statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadataStatement> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(RDFMutableMetadataContent.class))
            return (M) new RDFMutableMetadataStatement(_statement);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataStatementSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(RDFMetadataStatementSelector.class))
            return (S) new RDFMetadataStatementSelector(_statement);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getValue(RDFNode valueNode, Class<T> valueClass)
    {
        if (valueNode != null)
        {
            if (valueClass.isAssignableFrom(String.class) && valueNode.isLiteral())
                return (T) valueNode.asLiteral().getString();
            else if (valueClass.isAssignableFrom(Short.class) && valueNode.isLiteral())
                return (T) Short.valueOf(valueNode.asLiteral().getShort());
            else if (valueClass.isAssignableFrom(Integer.class) && valueNode.isLiteral())
                return (T) Integer.valueOf(valueNode.asLiteral().getInt());
            else if (valueClass.isAssignableFrom(Long.class) && valueNode.isLiteral())
                return (T) Long.valueOf(valueNode.asLiteral().getLong());
            else if (valueClass.isAssignableFrom(Float.class) && valueNode.isLiteral())
                return (T) Float.valueOf(valueNode.asLiteral().getFloat());
            else if (valueClass.isAssignableFrom(Double.class) && valueNode.isLiteral())
                return (T) Double.valueOf(valueNode.asLiteral().getDouble());
            else if (valueClass.getAnnotation(MetadataView.class) != null)
            {
            	RDFMutableMetadataContent rdfMutableMetadataContent = new RDFMutableMetadataContent(valueNode.asResource());

            	return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { valueClass }, new MutableMetadataContentViewInvocationHandler(rdfMutableMetadataContent));
            }
            else
                return null;
        }
        else
            return null;
    }

    protected Statement _statement;
}