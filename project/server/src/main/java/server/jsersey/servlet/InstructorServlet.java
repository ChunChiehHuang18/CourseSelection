package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.helper.MySqlConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;


/**
 * InstructorServlet handle instructor related request
 */
public class InstructorServlet extends BaseServlet {

    /**
     * Query all instructor list
     *
     * @return Instructor JSON Array
     */
    @Override
    public String queryAll() {
        return dbHelper.instructor().queryAll().toString();
    }

    /**
     * Query  instructor by instructor number
     *
     * @param number Instructor integer type number
     * @return Instructor  JSON array
     */
    @Override
    public String queryByNumber(String number) {
        return dbHelper.instructor().queryByNumber(number).toString();
    }

    /**
     * Query  course offered by instructor number
     *
     * @param number Instructor integer type number
     * @return Instructor  JSON array
     */
    @Path("{number}/course")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryCourseByInstructor(@PathParam("number") String number) {
        return dbHelper.course().queryByInstructor(Integer.valueOf(number)).toString();
    }

    /**
     * Add a instructor into DB
     *
     * @param postData instructor JSON data
     * @return Status code, 200: Success, 400: Failed
     */
    @Override
    public int add(String postData) {
        try {
            JSONObject instructorObj = new JSONObject(postData);
            int instructorNumber = -1;
            String instructorName;
            String instructorOffice = MySqlConfig.VALUE_NULL;

            // Get value
            if (instructorObj.has(KEY_INSTRUCTOR_NUMBER))
                instructorNumber = instructorObj.getInt(KEY_INSTRUCTOR_NUMBER);
            if (instructorObj.has(KEY_INSTRUCTOR_OFFCIE))
                instructorOffice = instructorObj.getString(KEY_INSTRUCTOR_OFFCIE);
            instructorName = instructorObj.getString(KEY_INSTRUCTOR_NAME);

            if (servletUtils.validInstructorData(instructorName, instructorOffice)) {
                // Insert student into db
                if (dbHelper.instructor().add(instructorNumber, instructorName, instructorOffice))
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

    @Override
    public int delete(String postData) {
        return Response.SC_BAD_REQUEST;
    }

}
