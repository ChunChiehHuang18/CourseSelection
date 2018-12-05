package server.mysql.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * CourseSelectionDBHelper provide higher level API to interact with MySQL
 */
public class CourseSelectionDBHelper {

    Connection conn = null;

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
    private PreparedStatement deductCourseRemainSelectStm = null;
    private PreparedStatement deductCourseRemainUpdateStm = null;
    private PreparedStatement queryStudentClasstimeStm = null;
    private PreparedStatement queryAllSelectionStm = null;
    private PreparedStatement queryCourseByStudentStm = null;
    private PreparedStatement queryCourseByInstructorStm = null;

    private static class SingletonHolder {
        private static final CourseSelectionDBHelper INSTANCE = new CourseSelectionDBHelper();
    }

    /**
     * Generate CourseSelectionDBHelper single instance
     * @return CourseSelectionDBHelper instance
     */
    public static CourseSelectionDBHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private CourseSelectionDBHelper() {
        try {
            // Register JDBC driver
            Class.forName(MySqlConfig.JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
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
            deductCourseRemainSelectStm = conn.prepareStatement(PrepareStatementUtils.deductCourseRemainSelectionString);
            deductCourseRemainUpdateStm = conn.prepareStatement(PrepareStatementUtils.deductCourseRemainUpdateString);
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


    /**
     * Add a instructor into MySql
     * @param instructorNumber Instructor's number
     * @param instructorName Instructor's name
     * @param office Instructor's office (can be null)
     * @return boolean
     */
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

    /**
     * Query all instructor list
     * @return Instructor JSONArray
     */
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

    /**
     * Query instructor by instructor's number
     * @param instructorNumber Instructor's number
     * @return Instructor JSONObject
     */
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

    /**
     * Add a student into MySql
     * @param studentNumber Student's number
     * @param studentName Student's name
     * @param gender Student's gender(Male, Female, Bisexual) (can be null)
     * @return boolean
     */
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

    /**
     * Query all student
     * @return Student JSONArray
     */
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

    /**
     * Query student by student number
     * @param studentNumber Student's number
     * @return Student JSONObject
     */
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


    /**
     * Add a Course into MySql
     * @param courseNumber Course's number(Fixed to 4 char)
     * @param courseTitle Course's title
     * @param instructorNumber Instructor's number
     * @param courseSize Course's size (10~ 255)
     * @param courseWeekday Course's week day (1 ~ 5)
     * @param courseClasstime Course's class time (1~8), can be multiple(2,3,4,)
     * @return boolean
     */
    public boolean addCourse(String courseNumber, String courseTitle, int instructorNumber, int courseSize, int courseWeekday, String courseClasstime) {
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
     * Query all course list
     * @return Course JSONArray
     */
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
     * @param courseNumber Course's number
     * @return Course JSONObject
     */
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
     * Select a Course and insert into MySql
     * @param selectionNumber Selection's number
     * @param studentNumber Student's number
     * @param courseNumber Course's number
     * @return boolean
     */
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

    /**
     * Query all selection list
     * @return Selection JSONArray
     */
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

    /**
     * Check duplicate selection
     * @param studentNumber Student's number
     * @param courseNumber Course's number
     * @return boolean
     */
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

    /**
     * Using locking reads to  deduct course remain and verify course selection times do not repeat
     * https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
     * @param courseNumber Course's number
     * @[param studentNumber Student's number
     * @return boolean
     */
    public boolean validClassTimeAndCourseRemain(int studentNumber, String courseNumber) {
        try {
            conn.setAutoCommit(false);
            // Get student's current class time
            JSONArray studenClasstime = queryStudentClasstime(studentNumber);
            boolean[][] classtimeOccupy = new boolean[5][8];
            for (boolean[] row: classtimeOccupy)
                Arrays.fill(row, Boolean.FALSE);
            for (int i = 0; i < studenClasstime.length(); i++) {
                int weekday = studenClasstime.getJSONObject(i).getInt(MySqlConfig.SHOW_COURSE_WEEKDAY);
                String[] classtime = studenClasstime.getJSONObject(i).getString(MySqlConfig.SHOW_COURSE_CLASSTIME).split(",");
                for (String time: classtime) {
                    classtimeOccupy[weekday - 1][Integer.valueOf(time) - 1] = Boolean.TRUE;
                }
            }

            // Get selection class time
            int selectiontWeekday = queryCourseByNumber(courseNumber).getInt(MySqlConfig.SHOW_COURSE_WEEKDAY);
            String[] selectionClassTime = queryCourseByNumber(courseNumber).getString(MySqlConfig.SHOW_COURSE_CLASSTIME).split(",");

            // Check class time occupy
            for (String time: selectionClassTime) {
                if(classtimeOccupy[selectiontWeekday - 1][Integer.valueOf(time) - 1]) {
                    conn.commit();
                    return false;
                }
            }

            // Try to deduct course remain and check affect row number
            deductCourseRemainSelectStm.setString(1, courseNumber);
            deductCourseRemainUpdateStm.setString(1, courseNumber);
            int affectedRow = 0;
            if(deductCourseRemainSelectStm.executeQuery().next()) {
                affectedRow =  deductCourseRemainUpdateStm.executeUpdate() ;
            }
            conn.commit();
            return affectedRow > 0;
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

    /**
     * Query student's all class time
     * @param studentNumber Student's number
     * @return Weekday and Class time JSONObject
     */
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
            obj.put(MySqlConfig.SHOW_COURSE_REMAIN, rs.getInt(MySqlConfig.COLUMN_COURSE_REMAIN));
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

    /**
     * Query selection by student's number
     * @param studentNumber Student's number
     * @return Detail course selection information
     */
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

    /**
     * Query selection by instructor's number
     * @param instructorNumber Instructor's number
     * @return Detail course selection information
     */
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
