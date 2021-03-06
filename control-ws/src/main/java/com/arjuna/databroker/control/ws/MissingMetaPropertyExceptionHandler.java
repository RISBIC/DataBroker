/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.arjuna.databroker.data.MissingMetaPropertyException;

@Provider
public class MissingMetaPropertyExceptionHandler implements ExceptionMapper<MissingMetaPropertyException>
{
    @Override
    public Response toResponse(MissingMetaPropertyException missingMetaPropertyException)
    {
        return Response.status(Status.BAD_REQUEST).entity(missingMetaPropertyException.getMessage()).build();
    }
}
