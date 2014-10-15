/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import com.arjuna.databroker.metadata.MetadataStatement;
import com.arjuna.databroker.metadata.MutableMetadataStatement;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataStatementSelector;
import com.arjuna.databroker.metadata.selectors.MetadataStatementSelector;
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
    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> valueClass)
    {
        if (_statement != null)
        {
            if (valueClass.isAssignableFrom(String.class))
                return (T) _statement.getString();
            else if (valueClass.isAssignableFrom(Integer.class))
                return (T) Integer.valueOf(_statement.getString());
            else
                return null;
        }
        else
            return null;
    }

    public <T> T getValue(Type valueType)
    {
        if (_statement != null)
        {
            if (valueType instanceof Class<?>)
                return getValue(valueType);
            else if (valueType instanceof ParameterizedType)
            {
                ParameterizedType parameterizedType = (ParameterizedType) valueType;
                if ((parameterizedType.getRawType() instanceof List) && (parameterizedType.getActualTypeArguments().length == 1))
                {
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

                    List<?> list = new LinkedList<Object>();
                    
                    // Process 'seq'
                    
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

    protected Statement _statement;
}