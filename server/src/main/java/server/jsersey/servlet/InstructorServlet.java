package server.jsersey.servlet;

import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.helper.CourseSelectionDBHelper;
import server.mysql.helper.MySqlConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;
import static server.jsersey.servlet.ServletUtils.FAIL;

@Path("/")
public class InstructorServlet {

    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryAllInstructor().toString();
    }

    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addInstructor(String postData) {
        try {
            JSONObject instructorObj = new JSONObject(postData);
            if (instructorObj.getString(KEY_ACTION).equalsIgnoreCase(ACTION_ADD)) {
                int instructorNumber = -1;
                String instructorName;
                String instructorOffice = MySqlConfig.VALUE_NULL;

                // Get value
                if(instructorObj.has(KEY_INSTRUCTOR_NUMBER))
                    instructorNumber = instructorObj.getInt(KEY_INSTRUCTOR_NUMBER);
                if(instructorObj.has(KEY_INSTRUCTOR_OFFCIE))
                    instructorOffice = instructorObj.getString(KEY_INSTRUCTOR_OFFCIE);
                instructorName = instructorObj.getString(KEY_INSTRUCTOR_NAME);

                if(validInstructorData(instructorName, instructorOffice)) {
                    // Insert student into db
                    CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
                    if (dbHelper.addInstructor(instructorNumber, instructorName, instructorOffice))
                        return SUCCESS;
                    else
                        return FAIL;
                } else
                    return FAIL;
            } else
                return FAIL;
        } catch (JSONException e) {
            e.printStackTrace();
            return FAIL;
        }
    }

}
