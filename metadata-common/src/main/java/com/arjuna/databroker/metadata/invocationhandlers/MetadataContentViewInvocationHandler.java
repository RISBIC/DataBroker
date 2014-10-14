/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.invocationhandlers;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;

import com.arjuna.databroker.metadata.MetadataContent;
import com.arjuna.databroker.metadata.annotations.MetadataStatementMapping;

public class MetadataContentViewInvocationHandler implements InvocationHandler
{
    public MetadataContentViewInvocationHandler(MetadataContent metadataContent)
    {
        _metadataContent = metadataContent;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        MetadataStatementMapping metadataStatementMapping = method.getAnnotation(MetadataStatementMapping.class);

        if (metadataStatementMapping == null)
            throw new UnsupportedOperationException("No annotation defined");
        else if (args != null)
            throw new UnsupportedOperationException("No arguments expected");
        else
            return _metadataContent.statement(metadataStatementMapping.name(), metadataStatementMapping.type()).getMetadataStatement().getValue(method.getReturnType());
    }

    private MetadataContent _metadataContent;
}