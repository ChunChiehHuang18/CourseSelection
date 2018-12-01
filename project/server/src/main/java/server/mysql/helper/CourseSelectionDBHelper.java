package server.mysql.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

/**
 * CourseSelectionDBHelper provide higher level API to use MySQL
 */
public class CourseSelectionDBHelper {

    // Instructor
    private PreparedStatement addInstructorStm = null;
    private PreparedStatement queryAllInstructorStm = null;
    private PreparedStatement queryInstructorByNumberStm = null;
    // Student
    private PreparedStatement addStudentStm = null;
    private PreparedStatement queryAllStudentStm = null;
    private PreparedStatement queryStudentByNumberStm = null;
    // Course
    private PreparedStatement addCourseStm = null;
    private PreparedStatement queryAllCourseStm = null;
    private PreparedStatement queryCourseByNumberStm = null;
    // Selection
    private PreparedStatement selectCourseStm = null;
    private PreparedStatement querySelectionDuplicateStm = null;
    private PreparedStatement querySelectionCountByCourseStm = null;
    private PreparedStatement queryStudentClasstimeStm = null;
    private PreparedStatement queryAllSelectionStm = null;
    private PreparedStatement queryCourseByStudentStm = null;
    private PreparedStatement queryCourseByInstructorStm = null;

    private static class SingletonHolder {
        private static final CourseSelectionDBHelper INSTANCE = new CourseSelectionDBHelper();
    }

    public static CourseSelectionDBHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private CourseSelectionDBHelper() {
        try {
            // Register JDBC driver
            Class.forName(MySqlConfig.JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            Connection conn = null;
            conn = DriverManager.getConnection(MySqlConfig.DB_URL, MySqlConfig.USER, MySqlConfig.PASS);

            System.out.println("Creating prepare statement...");
            // Instructor
            addInstructorStm = conn.prepareStatement(PrepareStatementUtils.addInstructorStmString);
            queryAllInstructorStm = conn.prepareStatement(PrepareStatementUtils.queryAllInstructorStmString);
            queryInstructorByNumberStm = conn.prepareStatement(PrepareStatementUtils.queryInstructorByNumberStmString);
            // Student
            addStudentStm = conn.prepareStatement(PrepareStatementUtils.addStudentStmString);
            queryAllStudentStm = conn.prepareStatement(PrepareStatementUtils.queryAllStudentStmString);
            queryStudentByNumberStm = conn.prepareStatement(PrepareStatementUtils.queryStudentByNumberStmString);
            // Course
            addCourseStm = conn.prepareStatement(PrepareStatementUtils.addCourseStmString);
            queryAllCourseStm = conn.prepareStatement(PrepareStatementUtils.queryAllCourseStmString);
            queryCourseByNumberStm = conn.prepareStatement(PrepareStatementUtils.queryCourseByNumberStmString);
            selectCourseStm = conn.prepareStatement(PrepareStatementUtils.selectCourseStmString);
            // Selection
            queryAllSelectionStm = conn.prepareStatement(PrepareStatementUtils.queryAllSelectionStmString);
            querySelectionDuplicateStm = conn.prepareStatement(PrepareStatementUtils.querySelectionDuplicateStmString);
            querySelectionCountByCourseStm = conn.prepareStatement(PrepareStatementUtils.querySelectionCountByCourseStmString);
            queryStudentClasstimeStm = conn.prepareStatement(PrepareStatementUtils.queryStudentClasstimeStmString);
            queryCourseByStudentStm = conn.prepareStatement(PrepareStatementUtils.queryCourseByStudentStmString);
            queryCourseByInstructorStm = conn.prepareStatement(PrepareStatementUtils.queryCourseByInstructorStmString);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean addInstructor(int instructorNumber, String instructorName, String office) {
        try {
            if (instructorNumber > 0)
                addInstructorStm.setInt(1, instructorNumber);
            else
                addInstructorStm.setNull(1, 0);
            addInstructorStm.setString(2, instructorName);
            if (!office.equalsIgnoreCase(MySqlConfig.VALUE_NULL))
                addInstructorStm.setString(3, office);
            else
                addInstructorStm.setNull(3, 0);
            addInstructorStm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JSONArray queryAllInstructor() {
        try {
            ResultSet rs = queryAllInstructorStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NAME, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_NAME));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_OFFICE, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_OFFICE));
                jsonArray.put(obj);
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public JSONObject queryInstructorByNumber(int instructorNumber) {
        try {
            queryInstructorByNumberStm.setInt(1, instructorNumber);
            ResultSet rs = queryInstructorByNumberStm.executeQuery();
            JSONObject obj = new JSONObject();
            while (rs.next()) {
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NAME, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_NAME));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_OFFICE, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_OFFICE));
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public boolean addStudent(int studentNumber, String studentName, String gender) {
        try {
            if (studentNumber > 0)
                addStudentStm.setInt(1, studentNumber);
            else
                addStudentStm.setNull(1, 0);
            addStudentStm.setString(2, studentName);
            if (!gender.equalsIgnoreCase(MySqlConfig.VALUE_NULL))
                addStudentStm.setString(3, gender);
            else
                addStudentStm.setNull(3, 0);
            addStudentStm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public JSONArray queryAllStudent() {
        try {
            ResultSet rs = queryAllStudentStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
                obj.put(MySqlConfig.SHOW_STUDENT_NAME, rs.getString(MySqlConfig.COLUMN_STUDENT_NAME));
                obj.put(MySqlConfig.SHOW_STUDENT_GENDER, rs.getString(MySqlConfig.COLUMN_STUDENT_GENDER));
                jsonArray.put(obj);
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public JSONObject queryStudentByNumber(int studentNumber) {
        try {
            queryStudentByNumberStm.setInt(1, studentNumber);
            ResultSet rs = queryStudentByNumberStm.executeQuery();
            JSONObject obj = new JSONObject();
            while (rs.next()) {
                obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
                obj.put(MySqlConfig.SHOW_STUDENT_NAME, rs.getString(MySqlConfig.COLUMN_STUDENT_NAME));
                obj.put(MySqlConfig.SHOW_STUDENT_GENDER, rs.getString(MySqlConfig.COLUMN_STUDENT_GENDER));
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }


    public boolean addCourse(String courseNumber, String courseTitle, int instructorNumber, int courseSize, int courseWeekday, String courseClasstime) {
        try {
            addCourseStm.setString(1, courseNumber);
            addCourseStm.setString(2, courseTitle);
            addCourseStm.setInt(3, instructorNumber);
            addCourseStm.setInt(4, courseSize);
            addCourseStm.setInt(5, courseWeekday);
            addCourseStm.setString(6, courseClasstime);
            addCourseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public JSONArray queryAllCourse() {
        try {
            ResultSet rs = queryAllCourseStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getInt(MySqlConfig.COLUMN_COURSE_SIZE));
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

    public JSONObject queryCourseByNumber(String courseNumber) {
        try {
            queryCourseByNumberStm.setString(1, courseNumber);
            ResultSet rs = queryCourseByNumberStm.executeQuery();
            JSONObject obj = new JSONObject();
            while (rs.next()) {
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
                obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getInt(MySqlConfig.COLUMN_COURSE_SIZE));
                obj.put(MySqlConfig.SHOW_COURSE_WEEKDAY, rs.getInt(MySqlConfig.COLUMN_COURSE_WEEKDAY));
                obj.put(MySqlConfig.SHOW_COURSE_CLASSTIME, rs.getString(MySqlConfig.COLUMN_COURSE_CLASSTIME));
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public boolean selectCourse(int selectionNumber, int studentNumber, String courseNumber) {
        try {
            if(selectionNumber > 0)
                selectCourseStm.setInt(1, selectionNumber);
            else
                selectCourseStm.setNull(1, 0);
            selectCourseStm.setString(2, courseNumber);
            selectCourseStm.setInt(3, studentNumber);
            selectCourseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public JSONArray queryAllSelection() {
        try {
            ResultSet rs = queryAllSelectionStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_SELECTION_NUMBER, rs.getInt(MySqlConfig.COLUMN_SELECTION_NUMBER));
                obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                jsonArray.put(obj);
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public boolean querySelectionDuplicate(int studentNumber, String courseNumber) {
        try {
            querySelectionDuplicateStm.setInt(1, studentNumber);
            querySelectionDuplicateStm.setString(2, courseNumber);
            ResultSet rs = querySelectionDuplicateStm.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) > 0;
            } else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int querySelectionCountByCourse(String courseNumber) {
        int selectionCount = -1;
        try {
            querySelectionCountByCourseStm.setString(1, courseNumber);
            ResultSet rs = querySelectionCountByCourseStm.executeQuery();
            if(rs.next()) {
                selectionCount = rs.getInt(1);
                return selectionCount;
            } else
                return selectionCount;

        } catch (SQLException e) {
            e.printStackTrace();
            return selectionCount;
        }
    }

    public JSONArray queryStudentClasstime(int studentNumber) {
        try {
            queryStudentClasstimeStm.setInt(1, studentNumber);
            ResultSet rs = queryStudentClasstimeStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
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


    private JSONObject getCourseSelectionJson(ResultSet rs) {
        try {
            JSONObject obj = new JSONObject();
            obj.put(MySqlConfig.SHOW_SELECTION_NUMBER, rs.getInt(MySqlConfig.COLUMN_SELECTION_NUMBER));
            obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
            obj.put(MySqlConfig.SHOW_STUDENT_NAME, rs.getString(MySqlConfig.COLUMN_STUDENT_NAME));
            obj.put(MySqlConfig.SHOW_STUDENT_GENDER, rs.getString(MySqlConfig.COLUMN_STUDENT_GENDER));
            obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
            obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
            obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getInt(MySqlConfig.COLUMN_COURSE_SIZE));
            obj.put(MySqlConfig.SHOW_COURSE_WEEKDAY, rs.getInt(MySqlConfig.COLUMN_COURSE_WEEKDAY));
            obj.put(MySqlConfig.SHOW_COURSE_CLASSTIME, rs.getString(MySqlConfig.COLUMN_COURSE_CLASSTIME));
            obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getInt(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
            obj.put(MySqlConfig.SHOW_INSTRUCTOR_NAME, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_NAME));
            obj.put(MySqlConfig.SHOW_INSTRUCTOR_OFFICE, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_OFFICE));
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray queryCourseSelectionByStudent(int studentNumber) {
        try {
            queryCourseByStudentStm.setInt(1, studentNumber);
            ResultSet rs = queryCourseByStudentStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = getCourseSelectionJson(rs);
                if (obj != null)
                    jsonArray.put(obj);
                else
                    return new JSONArray();
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONArray queryCourseSelectionByInstructor(int instructorNumber) {
        try {
            queryCourseByInstructorStm.setInt(1, instructorNumber);
            ResultSet rs = queryCourseByInstructorStm.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject obj = getCourseSelectionJson(rs);
                if (obj != null)
                    jsonArray.put(obj);
                else
                    return new JSONArray();
            }
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }


    public static void main(String[] args) {

        CourseSelectionDBHelper dbHelper = getInstance();
        ArrayList<JSONObject> jsonList;

//        System.out.println("Student list -- begin");
//        jsonList = dbHelper.queryAllStudent();
//        for (JSONObject obj:jsonList) {
//            System.out.println(obj.toString());
//        }
//        jsonList.clear();
//        System.out.println("Student list -- end");
//
//        System.out.println("Query by student -- begin");
//        jsonList = dbHelper.queryCourseSelectionByStudent(2);
//        for (JSONObject obj:jsonList) {
//            System.out.println(obj.toString());
//        }
//        jsonList.clear();
//        System.out.println("Query by student -- end");
//
//
//        System.out.println("Query by instructor -- begin");
//        jsonList = dbHelper.queryCourseSelectionByInstructor("Meryl Streep");
//        for (JSONObject obj:jsonList) {
//            System.out.println(obj.toString());
//        }
//        jsonList.clear();
//        System.out.println("Query by instructor -- end");


    }
}
