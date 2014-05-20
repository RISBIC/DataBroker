/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.client;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface ContentProxy
{
    @GET
    @Path("contents")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listMetadata(@QueryParam("requesterId") String requesterId, @QueryParam("userId") String userId);

    @GET
    @Path("/content/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMetadata(@PathParam("id") String id, @QueryParam("requesterId") String requesterId, @QueryParam("userId") String userId);

    @POST
    @Path("/contents")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postMetadata(@QueryParam("requesterId") String requesterId, @QueryParam("userId") String userId, String content);

    @POST
    @Path("/contents")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postMetadata(@QueryParam("parentId") String parentId, @QueryParam("requesterId") String requesterId, @QueryParam("userId") String userId, String content);

    @PUT
    @Path("/content/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void putMetadata(@PathParam("id") String id, @QueryParam("requesterId") String requesterId, @QueryParam("userId") String userId, String content);
}
