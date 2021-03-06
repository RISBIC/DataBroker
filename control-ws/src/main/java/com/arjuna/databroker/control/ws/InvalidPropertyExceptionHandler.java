/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.arjuna.databroker.data.InvalidPropertyException;

@Provider
public class InvalidPropertyExceptionHandler implements ExceptionMapper<InvalidPropertyException>
{
    @Override
    public Response toResponse(InvalidPropertyException invalidPropertyException)
    {
        return Response.status(Status.BAD_REQUEST).entity(invalidPropertyException.getMessage()).build();
    }
}
