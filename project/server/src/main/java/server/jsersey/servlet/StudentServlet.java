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
 * StudentServlet handle student related request
 */
@Path("/")
public class StudentServlet {

    /**
     * Query all student  list
     *
     * @return Student JSON array
     */
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryAll() {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return dbHelper.queryAllStudent().toString();
    }

    /**
     * Query  student by student number
     *
     * @param studentNumber Student integer type number
     * @return Student  JSON array
     */
    @Path("{studentNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryByNumber(@PathParam("studentNumber") String studentNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return dbHelper.queryStudentByNumber(Integer.valueOf(studentNumber)).toString();
    }

    /**
     * Query  courses selected by student number
     *
     * @param studentNumber Student integer type number
     * @return Detail course related  JSON array
     */
    @Path("{studentNumber}/selection")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String queryCourseByStudent(@PathParam("studentNumber") String studentNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        return dbHelper.querySelectionByStudent(Integer.valueOf(studentNumber)).toString();
    }

    /**
     * Add a student into DB
     *
     * @param postData Student JSON data
     * @return Status code
     */
    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public int addStudent(String postData) {
        try {
            JSONObject studentObj = new JSONObject(postData);
            int studentNumber = -1;
            String studentName;
            String studentGender = MySqlConfig.VALUE_NULL;

            // Get value
            if (studentObj.has(KEY_STUDENT_NUMBER))
                studentNumber = studentObj.getInt(KEY_STUDENT_NUMBER);
            if (studentObj.has(KEY_STUDENT_GENDER))
                studentGender = studentObj.getString(KEY_STUDENT_GENDER);
            studentName = studentObj.getString(KEY_STUDENT_NAME);

            if (ServletUtils.getInstance().validStudentData(studentName, studentGender)) {
                // Insert student into db
                CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
                if (dbHelper.addStudent(studentNumber, studentName, studentGender))
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
