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
    private static final Method OBJECT_EQUALS   = getObjectMethod("equals", Object.class);
    private static final Method OBJECT_HASHCODE = getObjectMethod("hashCode");
    private static final Method OBJECT_TOSTRING = getObjectMethod("toString");

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

        if (method.equals(OBJECT_EQUALS))
            throw new UnsupportedOperationException("'equals' not supported!");
        else if (method.equals(OBJECT_HASHCODE))
            throw new UnsupportedOperationException("'hashCode' not supported!");
        else if (method.equals(OBJECT_TOSTRING))
            return "View-" + _mutableMetadataContent;
        else if ((getMetadataMapping == null) && (setMetadataMapping == null))
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

    private MutableMetadataContent _mutableMetadataContent;
}
