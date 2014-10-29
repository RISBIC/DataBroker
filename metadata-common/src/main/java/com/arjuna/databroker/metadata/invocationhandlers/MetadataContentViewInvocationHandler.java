/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.invocationhandlers;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;

import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;

public class MetadataContentViewInvocationHandler implements InvocationHandler
{
    private static final Method OBJECT_EQUALS   = getObjectMethod("equals", Object.class);
    private static final Method OBJECT_HASHCODE = getObjectMethod("hashCode");
    private static final Method OBJECT_TOSTRING = getObjectMethod("toString");
    
    public MetadataContentViewInvocationHandler(MetadataContent metadataContent)
    {
        _metadataContent = metadataContent;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        GetMetadataMapping getMetadataMapping = method.getAnnotation(GetMetadataMapping.class);

        if ((method == OBJECT_EQUALS))
            throw new UnsupportedOperationException("'equals' not supported!");
        else if (method == OBJECT_HASHCODE)
            throw new UnsupportedOperationException("'hashCode' not supported!");
        else if (method == OBJECT_TOSTRING)
            return "View-" + _metadataContent;
        else if (getMetadataMapping == null)
            throw new UnsupportedOperationException("No annotation defined");
        else if (args != null)
            throw new UnsupportedOperationException("No arguments expected");
        else
            return _metadataContent.statement(getMetadataMapping.name(), getMetadataMapping.type()).getMetadataStatement().getValue(method.getGenericReturnType());
    }

    private static Method getObjectMethod(String methodName, Class<?>... argumentTypes)
    {
        try
        {
            return Object.class.getMethod(methodName, argumentTypes);
        }
        catch (NoSuchMethodException noSuchMethodException)
        {
            throw new IllegalArgumentException(noSuchMethodException);
        }
    }

    private MetadataContent _metadataContent;
}
