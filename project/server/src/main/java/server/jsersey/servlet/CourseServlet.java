package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.helper.CourseSelectionDBHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;

/**
 * CourseServlet handle course related request
 */
@Path("/")
public class CourseServlet {

    /**
     * Query all course list
     * @return Course JSON array
     */
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return dbHelper.queryAllCourse().toString();
    }

    /**
     * Query  course by course number
     * @param courseNumber Course String type number
     * @return Course  JSON array
     */
    @Path("{courseNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByNumber(@PathParam("courseNumber") String courseNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryCourseByNumber(courseNumber).toString();
    }

    /**
     * Add a course data into DB
     * @param postData Course JSON data
     * @return Status code
     */
    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public int addCourse(String postData) {
        try {
            JSONObject courseObj = new JSONObject(postData);
            if (courseObj.getString(KEY_ACTION).equalsIgnoreCase(ACTION_ADD)) {
                String courseNumber;
                String courseTitle;
                int instructorNumber;
                int courseSize;
                int courseWeekday;
                String courseClasstime;

                // Get value
                courseNumber = courseObj.getString(KEY_COURSE_NUMBER);
                courseTitle = courseObj.getString(KEY_COURSE_TITLE);
                instructorNumber = courseObj.getInt(KEY_INSTRUCTOR_NUMBER);
                courseSize = courseObj.getInt(KEY_COURSE_SIZE);
                courseWeekday = courseObj.getInt(KEY_COURSE_WEEKDAY);
                courseClasstime = courseObj.getString(KEY_COURSE_CLASSTIME);


                if (validCourseData(courseNumber, courseTitle, instructorNumber, courseSize, courseWeekday, courseClasstime)) {
                    // Insert student into db
                    CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
                    if (dbHelper.addCourse(courseNumber, courseTitle, instructorNumber, courseSize, courseWeekday, courseClasstime))
                        return Response.SC_OK;
                    else
                        return Response.SC_BAD_REQUEST;
                } else
                    return Response.SC_BAD_REQUEST;
            } else
                return Response.SC_BAD_REQUEST;
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.SC_BAD_REQUEST;
        }
    }
}
