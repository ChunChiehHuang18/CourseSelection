package server.jsersey.servlet;

import server.mysql.helper.CourseSelectionDBHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * SelectionServlet handle selection course related request
 */
@Path("/")
public class SelectionServlet {

    /**
     * Query all selection course list
     * @return Course JSON array
     */
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryAllSelection().toString();
    }

    /**
     * Query all selection course list by student number
     * @return Detail course related  JSON array
     */
    @Path("student/{studentNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByStudent(@PathParam("studentNumber") String studentNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryCourseSelectionByStudent(Integer.valueOf(studentNumber)).toString();
    }

    /**
     * Query all selection course list by instructor number
     * @return Detail course related  JSON array
     */
    @Path("instructor/{instructorNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByInstructor(@PathParam("instructorNumber") String instructorNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryCourseSelectionByInstructor(Integer.valueOf(instructorNumber)).toString();
    }
}
