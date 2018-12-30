package server.jsersey.servlet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public abstract class BaseServlet {

    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public abstract String queryAll();

    @Path("{number}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public abstract String queryByNumber(@PathParam("number") String number);

    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract int add(String postData);

    @Path("/")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract int delete(String postData);
}
