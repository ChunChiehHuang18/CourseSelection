package server.mysql.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static server.mysql.helper.PrepareStatementUtils.*;

/**
 * Providing course related JDBC API
 */
public class CourseDbHelper implements IDbHelper {

    private Connection conn;
    private ClasstimeDbHelper classtimeDbHelper = null;

    // add
    private PreparedStatement addCourseStm = null;
    // delete
    private PreparedStatement deleteCourseStm = null;
    private PreparedStatement deleteSelectionByCourseStm = null;
    // query
    private PreparedStatement queryAllCourseStm = null;
    private PreparedStatement queryCourseByNumberStm = null;
    private PreparedStatement queryCourseByInstructorStm = null;
    private PreparedStatement querySelectionByCourseStm = null;

    CourseDbHelper(Connection conn) {
        try {
            this.conn = conn;

            // add
            addCourseStm = conn.prepareStatement(ADD_COURSE_STM_STRING);
            // delete
            deleteCourseStm = conn.prepareStatement(DELETE_COURSE_STM_STRING);
            deleteSelectionByCourseStm = conn.prepareStatement(DELETE_SELECTION_BY_COURSE_STM_STRING);
            // query
            queryAllCourseStm = conn.prepareStatement(QUERY_ALL_COURSE_STM_STRING);
            queryCourseByNumberStm = conn.prepareStatement(QUERY_COURSE_BY_NUMBER_STM_STRING);
            queryCourseByInstructorStm = conn.prepareStatement(QUERY_COURSE_BY_INSTRUCTOR_STM_STRING);
            querySelectionByCourseStm = conn.prepareStatement(QUERY_SELECTION_BY_COURSE_STM_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setClasstimeDbHelper(ClasstimeDbHelper dbHelper) {
        this.classtimeDbHelper = dbHelper;
    }

    /**
     * Query all course list
     * @return Course JSONArray
     */
    @Override
    public JSONArray queryAll() {
        try {
            ResultSet rs = queryAllCourseStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getInt(MySqlConfig.COLUMN_COURSE_SIZE));
                obj.put(MySqlConfig.SHOW_COURSE_REMAIN, rs.getInt(MySqlConfig.COLUMN_COURSE_REMAIN));
                obj.put(MySqlConfig.SHOW_COURSE_WEEKDAY, rs.getInt(MySqlConfig.COLUMN_COURSE_WEEKDAY));
                obj.put(MySqlConfig.SHOW_COURSE_CLASSTIME, rs.getString(MySqlConfig.COLUMN_COURSE_CLASSTIME));
                jsonArray.put(obj);
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /**
     * Query course by course's number
     * @param number Course's number
     * @return Course JSONObject
     */
    @Override
    public JSONObject queryByNumber(String number) {
        try {
            queryCourseByNumberStm.setString(1, number);
            ResultSet rs = queryCourseByNumberStm.executeQuery();
            JSONObject obj = new JSONObject();
            while (rs.next()) {
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getInt(MySqlConfig.COLUMN_COURSE_SIZE));
                obj.put(MySqlConfig.SHOW_COURSE_REMAIN, rs.getInt(MySqlConfig.COLUMN_COURSE_REMAIN));
                obj.put(MySqlConfig.SHOW_COURSE_WEEKDAY, rs.getInt(MySqlConfig.COLUMN_COURSE_WEEKDAY));
                obj.put(MySqlConfig.SHOW_COURSE_CLASSTIME, rs.getString(MySqlConfig.COLUMN_COURSE_CLASSTIME));
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /**
     * Query course by instructor's number
     * @param instructorNumber Course's number
     * @return Course JSONArray
     */
    public JSONArray queryByInstructor(int instructorNumber) {
        try {
            queryCourseByInstructorStm.setInt(1, instructorNumber);
            ResultSet rs = queryCourseByInstructorStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getInt(MySqlConfig.COLUMN_COURSE_SIZE));
                obj.put(MySqlConfig.SHOW_COURSE_REMAIN, rs.getInt(MySqlConfig.COLUMN_COURSE_REMAIN));
                obj.put(MySqlConfig.SHOW_COURSE_WEEKDAY, rs.getInt(MySqlConfig.COLUMN_COURSE_WEEKDAY));
                obj.put(MySqlConfig.SHOW_COURSE_CLASSTIME, rs.getString(MySqlConfig.COLUMN_COURSE_CLASSTIME));
                jsonArray.put(obj);
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    /**
     * Add a Course into MySql
     * @param courseNumber Course's number(Fixed to 5 char)
     * @param courseTitle Course's title
     * @param instructorNumber Instructor's number
     * @param courseSize Course's size (10~ 255)
     * @param courseWeekday Course's week day (1 ~ 5)
     * @param courseClasstime Course's class time (1~8), can be multiple(2,3,4,)
     * @return True: Success, False: Failed
     */
    public boolean add(String courseNumber, String courseTitle, int instructorNumber, int courseSize, int courseWeekday, String courseClasstime) {
        try {
            addCourseStm.setString(1, courseNumber);
            addCourseStm.setString(2, courseTitle);
            addCourseStm.setInt(3, instructorNumber);
            addCourseStm.setInt(4, courseSize);
            addCourseStm.setInt(5, courseSize);
            addCourseStm.setInt(6, courseWeekday);
            addCourseStm.setString(7, courseClasstime);
            addCourseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete the course and course's selection data
     * 1. Get selection number array
     * 2. Delete selection by course number
     * 3. Remove student class time
     * 4. Delete course
     * @param number Course's number(Fixed to 5 char)
     * @return True: Success, False: Failed
     */
    @Override
    public boolean delete(String number) {
        try {
            // 1. Get selection number array
            querySelectionByCourseStm.setString(1, number);
            ResultSet selectionRs = querySelectionByCourseStm.executeQuery();
            ArrayList<Integer> studentNumberArray = new ArrayList<>();
            while (selectionRs.next()) {
                studentNumberArray.add(selectionRs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
            }

            conn.setAutoCommit(false);

            // 2. Delete selection by course number
            deleteSelectionByCourseStm.setString(1, number);
            deleteSelectionByCourseStm.executeUpdate();

            // 3. Remove student class time
            for (int studentNumber : studentNumberArray) {
                if(classtimeDbHelper == null ||
                        !classtimeDbHelper.updateClasstime(number, studentNumber, ClasstimeDbHelper.UpdateMode.DELETE)) {
                    conn.rollback();
                    conn.commit();
                    return false;
                }
            }

            // 4. Delete course
            deleteCourseStm.setString(1, number);
            if(deleteCourseStm.executeUpdate() <= 0) {
                conn.rollback();
                conn.commit();
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch(SQLException excep) {
                    excep.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
