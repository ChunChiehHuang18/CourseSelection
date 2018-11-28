package server.jsersey.servlet;

import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.helper.CourseSelectionDBHelper;
import server.mysql.helper.MySqlConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;

@Path("/")
public class StudentServlet {

    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return  dbHelper.queryAllStudent().toString();
    }

    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStudent(String postData) {
        try {
            JSONObject studentObj = new JSONObject(postData);
            if (studentObj.getString(KEY_ACTION).equalsIgnoreCase(ACTION_ADD)) {
                int studentNumber = -1;
                String studentName;
                String studentGender = MySqlConfig.VALUE_NULL;

                // Get value
                if(studentObj.has(KEY_STUDENT_NUMBER))
                    studentNumber = studentObj.getInt(KEY_STUDENT_NUMBER);
                if(studentObj.has(KEY_STUDENT_GENDER)) {
                    String gender = studentObj.getString(KEY_STUDENT_GENDER);
                    if(gender.equalsIgnoreCase(GENDER_MALE) || gender.equalsIgnoreCase(GENDER_FEMALE))
                        studentGender = gender;
                }
                studentName = studentObj.getString(KEY_STUDENT_NAME);

                // Insert student into db
                CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
                if (dbHelper.addStudent(studentNumber, studentName, studentGender))
                    return SUCCESS;
                else
                    return FAIL;
            }
            return studentObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return FAIL;
        }
    }

}
