package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import server.mysql.MyDbHelper;
import server.mysql.dbhelper.IDbHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public abstract class BaseServlet {

    MyDbHelper dbHelper = MyDbHelper.getInstance();
    ServletUtils servletUtils = ServletUtils.getInstance();

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

    /**
     * Query all of polymorphism API
     * @param dbHelper Object implement IDbHelper Interface
     * @return JSONArray String
     */
    String queryAll(IDbHelper dbHelper) {
        return dbHelper.queryAll().toString();
    }

    /**
     * Query by number of polymorphism API
     * @param dbHelper Object implement IDbHelper Interface
     * @param number The number of element you want to query
     * @return JSON String
     */
    String queryByNumber(IDbHelper dbHelper, String number) {
        return dbHelper.queryByNumber(number).toString();
    }

    /**
     * Delete by number of polymorphism API
     * @param dbHelper Object implement IDbHelper Interface
     * @param number The number of element you want to delete
     * @return Status code, 200: Success, 400: Failed
     */
    int delete(IDbHelper dbHelper, String number) {
        if (dbHelper.validDeleteData(number)) {
            if (dbHelper.delete(number))
                return Response.SC_OK;
            else
                return Response.SC_BAD_REQUEST;
        } else
            return Response.SC_BAD_REQUEST;
    }

}
