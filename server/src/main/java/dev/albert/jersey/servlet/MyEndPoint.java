package dev.albert.jersey.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ep")
public class MyEndPoint {

    @Path("1")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String ping() {
        return "Hello from ping";
    }
}
