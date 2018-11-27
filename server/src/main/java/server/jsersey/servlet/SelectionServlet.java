package server.jsersey.servlet;

import server.mysql.helper.CourseSelectionDBHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class SelectionServlet {

    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryAllSelection().toString();
    }

    @Path("student/{studentNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByStudent(@PathParam("studentNumber") String studentNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryCourseSelectionByStudent(Integer.valueOf(studentNumber)).toString();
    }

    @Path("instructor/{instructorNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByInstructor(@PathParam("instructorNumber") String instructorNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryCourseSelectionByInstructor(Integer.valueOf(instructorNumber)).toString();
    }
}
