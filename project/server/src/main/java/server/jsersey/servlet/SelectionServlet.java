package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.helper.CourseSelectionDBHelper;

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
     *
     * @return Course JSON array
     */
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return dbHelper.queryAllSelection().toString();
    }

    /**
     * Query all selection course list by student number
     *
     * @param studentNumber    Student integer type number
     * @param instructorNumber Instructor integer type number
     * @return Detail course related  JSON array
     */
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByStudent(@QueryParam("studentid") int studentNumber,
                                 @QueryParam("instructorid") int instructorNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        if (studentNumber != 0 && instructorNumber != 0)
            return dbHelper.querySelectionByStudentAndInstructor(studentNumber, instructorNumber).toString();
        else if (studentNumber != 0)
            return dbHelper.querySelectionByStudent(studentNumber).toString();
        else if (instructorNumber != 0)
            return dbHelper.querySelectionByInstructor(instructorNumber).toString();
        else
            return new JSONArray().toString();
    }

    /**
     * Select a course and insert into DB
     *
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
            int selectionNumber = -1;
            // Get value
            if (selectionObj.has(KEY_SELECTION_NUMBER))
                selectionNumber = selectionObj.getInt(KEY_SELECTION_NUMBER);
            int studentNumber = selectionObj.getInt(KEY_STUDENT_NUMBER);
            String courseNumber = selectionObj.getString(KEY_COURSE_NUMBER);

            if (ServletUtils.getInstance().validSelectionData(studentNumber, courseNumber)) {
                // Insert selection into db
                CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
                if (dbHelper.addSelection(selectionNumber, studentNumber, courseNumber))
                    return Response.SC_OK;
                else
                    return Response.SC_BAD_REQUEST;
            } else
                return Response.SC_BAD_REQUEST;
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.SC_BAD_REQUEST;
        }
    }


    /**
     * Delete the selection
     *
     * @param postData Select course JSON data
     * @return Status code
     */
    @Path("/")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public int deleteSelection(String postData) {
        try {
            JSONObject selectionObj = new JSONObject(postData);

            // Get value
            int selectionNumber = selectionObj.getInt(KEY_SELECTION_NUMBER);

            if (ServletUtils.getInstance().validDeleteData(selectionNumber)) {
                // Delete selection
                CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
                if (dbHelper.deleteSelection(selectionNumber))
                    return Response.SC_OK;
                else
                    return Response.SC_BAD_REQUEST;
            } else
                return Response.SC_BAD_REQUEST;
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.SC_BAD_REQUEST;
        }
    }
}
