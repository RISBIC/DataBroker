/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.invocationhandlers;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import com.arjuna.databroker.metadata.MutableMetadataContent;
import com.arjuna.databroker.metadata.annotations.GetMetadataMapping;
import com.arjuna.databroker.metadata.annotations.SetMetadataMapping;

public class MutableMetadataContentViewInvocationHandler implements InvocationHandler
{
    public MutableMetadataContentViewInvocationHandler(MutableMetadataContent mutableMetadataContent)
    {
        _mutableMetadataContent = mutableMetadataContent;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        GetMetadataMapping getMetadataMapping = method.getAnnotation(GetMetadataMapping.class);
        SetMetadataMapping setMetadataMapping = method.getAnnotation(SetMetadataMapping.class);

        if ((getMetadataMapping == null) && (setMetadataMapping == null))
            throw new UnsupportedOperationException("No annotation defined");
        else if (getMetadataMapping != null)
        {
            if (args != null)
                throw new UnsupportedOperationException("No arguments expected");
            else
                return _mutableMetadataContent.mutableStatement(getMetadataMapping.name(), getMetadataMapping.type()).getMutableMetadataStatement().getValue(method.getGenericReturnType());
        }
        else
        {
            if ((args == null) || (args.length != 1))
                throw new UnsupportedOperationException("Arguments expected");
            else
            {
                _mutableMetadataContent.mutableStatement(setMetadataMapping.name(), setMetadataMapping.type()).getMutableMetadataStatement().setValue(args[0], method.getGenericParameterTypes()[0]);

                return null;
            }
        }
    }

    private MutableMetadataContent _mutableMetadataContent;
}