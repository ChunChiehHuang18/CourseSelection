package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;

/**
 * SelectionServlet handle selection course related request
 */
public class SelectionServlet extends BaseServlet {

    /**
     * Query all selection course list
     *
     * @return Course JSON array
     */
    @Override
    public String queryAll() {
        return dbHelper.queryAllSelection().toString();
    }

    /**
     * Query  selection by selection number
     *
     * @return Course JSONObject
     */

    @Override
    public String queryByNumber(String number) {
        return dbHelper.querySelectionByNumber(Integer.valueOf(number)).toString();
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
     * Add a selection and insert into DB
     *
     * @param postData Select course JSON data
     * @return Status code, 200: Success, 400: Failed
     */
    @Override
    public int add(String postData) {
        try {
            JSONObject selectionObj = new JSONObject(postData);
            int selectionNumber = -1;
            // Get value
            if (selectionObj.has(KEY_SELECTION_NUMBER))
                selectionNumber = selectionObj.getInt(KEY_SELECTION_NUMBER);
            int studentNumber = selectionObj.getInt(KEY_STUDENT_NUMBER);
            String courseNumber = selectionObj.getString(KEY_COURSE_NUMBER);

            if (servletUtils.validSelectionData(studentNumber, courseNumber)) {
                // Insert selection into db
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
     * @return Status code, 200: Success, 400: Failed
     */
    @Override
    public int delete(String postData) {
        try {
            JSONObject selectionObj = new JSONObject(postData);

            // Get value
            int selectionNumber = selectionObj.getInt(KEY_SELECTION_NUMBER);

            if (servletUtils.validDeleteSelectionData(selectionNumber)) {
                // Delete selection
                if (dbHelper.deleteSelectionByNumber(selectionNumber))
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
