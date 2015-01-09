/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import com.arjuna.databroker.metadata.MutableMetadataStatement;
import com.arjuna.databroker.metadata.annotations.MetadataView;
import com.arjuna.databroker.metadata.invocationhandlers.MutableMetadataContentViewInvocationHandler;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFMutableMetadataStatement extends RDFMetadataStatement implements MutableMetadataStatement
{
    public RDFMutableMetadataStatement(Statement statement)
    {
        super(statement);
    }

    @Override
    public <T> void setValue(T value, Class<T> valueClass)
    {
        if (_statement != null)
            setValue(_statement, valueClass);
    }

    @SuppressWarnings("unchecked")
    public <T> void setValue(T value, Type valueType)
    {
        if (_statement != null)
        {
            if (valueType instanceof Class<?>)
                setValue(_statement, value, (Class<T>) valueType);
            else if (valueType instanceof ParameterizedType)
            {
                ParameterizedType parameterizedType = (ParameterizedType) valueType;

                if (parameterizedType.getRawType().equals(List.class) && (parameterizedType.getActualTypeArguments().length == 1))
                {
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

                    List<Object> list        = new LinkedList<Object>();
                    NodeIterator bagIterator = _statement.getBag().iterator();
                    // TODO
//                    while (bagIterator.hasNext())
//                        setValue(bagIterator.next(), (Class<?>) actualTypeArgument));
                }
            }
        }
    }

    private static <T> void setValue(Statement valueStatement, T value, Class<T> valueClass)
    {
        if (valueStatement != null)
        {
            if (valueClass.isAssignableFrom(String.class))
                valueStatement.changeObject((String) value, StandardCharsets.UTF_8.name());
            else if (valueClass.isAssignableFrom(Short.class))
                valueStatement.changeLiteralObject((Short) value);
            else if (valueClass.isAssignableFrom(Integer.class))
                valueStatement.changeLiteralObject((Integer) value);
            else if (valueClass.isAssignableFrom(Long.class))
                valueStatement.changeLiteralObject((Long) value);
            else if (valueClass.isAssignableFrom(Float.class))
                valueStatement.changeLiteralObject((Float) value);
            else if (valueClass.isAssignableFrom(Double.class))
                valueStatement.changeLiteralObject((Double) value);
            else if (valueClass.getAnnotation(MetadataView.class) != null)
            {
                RDFMutableMetadataContent rdfMutableMetadataContent = new RDFMutableMetadataContent(valueStatement.getResource());

                Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { valueClass }, new MutableMetadataContentViewInvocationHandler(rdfMutableMetadataContent));
            }
        }
    }
}