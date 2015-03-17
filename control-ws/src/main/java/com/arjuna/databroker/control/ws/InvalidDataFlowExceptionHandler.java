/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.arjuna.databroker.data.InvalidDataFlowException;

@Provider
public class InvalidDataFlowExceptionHandler implements ExceptionMapper<InvalidDataFlowException>
{
    @Override
    public Response toResponse(InvalidDataFlowException invalidDataFlowException)
    {
        return Response.status(Status.BAD_REQUEST).entity(invalidDataFlowException.getMessage()).build();
    }
}
