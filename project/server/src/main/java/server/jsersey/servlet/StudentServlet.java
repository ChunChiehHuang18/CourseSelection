package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONException;
import org.json.JSONObject;
import server.mysql.utils.MySqlConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static server.jsersey.servlet.ServletUtils.*;

/**
 * StudentServlet handle student related request
 */
public class StudentServlet extends BaseServlet {

    /**
     * Query all student  list
     *
     * @return Student JSON array
     */
    @Override
    public String queryAll() {
        return servletUtils.queryAll(dbHelper.student());
    }

    /**
     * Query  student by student number
     *
     * @param number Student integer type number
     * @return Student  JSON array
     */
    @Override
    public String queryByNumber(String number) {
        return servletUtils.queryByNumber(dbHelper.student(), number);
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
        return dbHelper.selection().queryByStudent(Integer.valueOf(studentNumber)).toString();
    }

    /**
     * Add a student into DB
     *
     * @param postData Student JSON data
     * @return Status code, 200: Success, 400: Failed
     */
    @Override
    public int add(String postData) {
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

            if (servletUtils.validStudentData(studentName, studentGender)) {
                // Insert student into db
                if (dbHelper.student().add(studentNumber, studentName, studentGender))
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
