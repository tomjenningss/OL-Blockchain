package io.openliberty.guides.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("helloworld")
public class Echo {
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	public String getContent() {
        return "Hello world";
    }
}