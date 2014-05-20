/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.client;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

public interface DataProxy
{
    @PUT
    @Path("/endpoints/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void receiveData(@PathParam("id") String id, String data);
}
