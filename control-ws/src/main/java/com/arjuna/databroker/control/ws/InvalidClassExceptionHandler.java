/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.arjuna.databroker.data.InvalidClassException;

@Provider
public class InvalidClassExceptionHandler implements ExceptionMapper<InvalidClassException>
{
    @Override
    public Response toResponse(InvalidClassException invalidClassException)
    {
        return Response.status(Status.BAD_REQUEST).entity(invalidClassException.getMessage()).build();
    }
}
