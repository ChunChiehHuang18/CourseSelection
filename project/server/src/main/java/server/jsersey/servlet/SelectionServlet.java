package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.helper.CourseSelectionDBHelper;
import server.mysql.helper.MySqlConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;

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
     * @param studentNumber Student integer type number
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
     * @param instructorNumber Instructor integer type number
     * @return Detail course related  JSON array
     */
    @Path("instructor/{instructorNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByInstructor(@PathParam("instructorNumber") String instructorNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryCourseSelectionByInstructor(Integer.valueOf(instructorNumber)).toString();
    }

    /**
     * Select a course and insert into DB
     * @param postData Select course JSON data
     * @return Status code
     */
    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public int selectCourse(String postData) {
        try {
            JSONObject selectionObj = new JSONObject(postData);
            if (selectionObj.getString(KEY_ACTION).equalsIgnoreCase(ACTION_ADD)) {
                int studentNumber;
                String courseNumber;

                // Get value
                studentNumber = selectionObj.getInt(KEY_STUDENT_NUMBER);
                courseNumber = selectionObj.getString(KEY_COURSE_NUMBER);
                if (validSelectionData(studentNumber, courseNumber))
                    return Response.SC_OK;
                else
                    return Response.SC_BAD_REQUEST;
//                if (validSelectionData(studentNumber, courseNumber)) {
//                    // Insert student into db
//                    CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
//                    if (dbHelper.selectCourse(studentNumber, courseNumber))
//                        return Response.SC_OK;
//                    else
//                        return Response.SC_BAD_REQUEST;
//                } else
//                    return Response.SC_BAD_REQUEST;
            } else
                return Response.SC_BAD_REQUEST;
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.SC_BAD_REQUEST;
        }
    }
}
