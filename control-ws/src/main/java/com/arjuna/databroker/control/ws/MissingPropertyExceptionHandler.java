/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.arjuna.databroker.data.MissingPropertyException;

@Provider
public class MissingPropertyExceptionHandler implements ExceptionMapper<MissingPropertyException>
{
    @Override
    public Response toResponse(MissingPropertyException missingPropertyException)
    {
        return Response.status(Status.BAD_REQUEST).entity(missingPropertyException.getMessage()).build(); 
    }
}
