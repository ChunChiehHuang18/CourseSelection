package server.jsersey.servlet;

import org.eclipse.jetty.server.Response;
import org.json.JSONException;
import org.json.JSONObject;

import static server.jsersey.servlet.ServletUtils.*;

/**
 * CourseServlet handle course related request
 */
public class CourseServlet extends BaseServlet {


    /**
     * Query all course list
     *
     * @return Course JSON array
     */
    @Override
    public String queryAll() {
        return servletUtils.queryAll(dbHelper.course());
    }

    /**
     * Query  course by course number
     *
     * @param number Course String type number
     * @return Course  JSON array
     */
    @Override
    public String queryByNumber(String number) {
        return servletUtils.queryByNumber(dbHelper.course(), number);
    }

    /**
     * Add a course data into DB
     *
     * @param postData Course JSON data
     * @return Status code, 200: Success, 400: Failed
     */
    @Override
    public int add(String postData) {
        try {
            JSONObject courseObj = new JSONObject(postData);
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


            if (servletUtils.validCourseData(courseNumber, courseTitle, instructorNumber, courseSize, courseWeekday, courseClasstime)) {
                // Insert student into db
                if (dbHelper.course().add(courseNumber, courseTitle, instructorNumber, courseSize, courseWeekday, courseClasstime))
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
     * Delete a course data
     *
     * @param postData Course JSON data
     * @return Status code, 200: Success, 400: Failed
     */
    @Override
    public int delete(String postData) {
        try {
            JSONObject courseObj = new JSONObject(postData);
            String courseNumber;
            // Get value
            courseNumber = courseObj.getString(KEY_COURSE_NUMBER);


            if (servletUtils.validDeleteCourseData(courseNumber)) {
                return servletUtils.delete(dbHelper.course(), courseNumber);
            } else
                return Response.SC_BAD_REQUEST;
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.SC_BAD_REQUEST;
        }
    }

}
