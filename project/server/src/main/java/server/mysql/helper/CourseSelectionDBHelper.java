package server.mysql.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

import static server.mysql.helper.PrepareStatementUtils.*;


/**
 * CourseSelectionDBHelper provide higher level API to interact with MySQL
 */
public class CourseSelectionDBHelper {

    private Connection conn = null;

    // Instructor
    // add
    private PreparedStatement addInstructorStm = null;
    // query
    private PreparedStatement queryAllInstructorStm = null;
    private PreparedStatement queryInstructorByNumberStm = null;

    // Student
    // add
    private PreparedStatement addStudentStm = null;
    // query
    private PreparedStatement queryAllStudentStm = null;
    private PreparedStatement queryStudentByNumberStm = null;
    // class time
    private enum UpdateMode {
        ADD, DELETE
    }

    // Course
    // add
    private PreparedStatement addCourseStm = null;
    //
    private PreparedStatement deleteCourseStm = null;
    // query
    private PreparedStatement queryAllCourseStm = null;
    private PreparedStatement queryCourseByNumberStm = null;
    private PreparedStatement queryCourseByInstructorStm = null;

    // Selection
    // add
    private PreparedStatement deductCourseRemainSelectStm = null;
    private PreparedStatement deductCourseRemainUpdateStm = null;
    private PreparedStatement addSelectionStm = null;
    private PreparedStatement studentClasstimeSelectStm = null;
    private PreparedStatement studentClasstimeUpdateStm = null;
    // delete
    private PreparedStatement addCourseRemainSelectionStm = null;
    private PreparedStatement addCourseRemainUpdateStm = null;
    private PreparedStatement deleteSelectionByNumberStm = null;
    private PreparedStatement deleteSelectionByCourseStm = null;
    // query
    private PreparedStatement queryAllSelectionStm = null;
    private PreparedStatement querySelectionByCourseStm = null;
    private PreparedStatement querySelectionByNumberStm = null;
    private PreparedStatement querySelectionByStudentStm = null;
    private PreparedStatement querySelectionByInstructorStm = null;
    private PreparedStatement querySelectionByStudentAndInstructorStm = null;

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
            // add
            addInstructorStm = conn.prepareStatement(ADD_INSTRUCTOR_STM_STRING);
            // query
            queryAllInstructorStm = conn.prepareStatement(QUERY_ALL_INSTRUCTOR_STM_STRING);
            queryInstructorByNumberStm = conn.prepareStatement(QUERY_INSTRUCTOR_BY_NUMBER_STM_STRING);
            // Student
            // add
            addStudentStm = conn.prepareStatement(ADD_STUDENT_STM_STRING);
            // query
            queryAllStudentStm = conn.prepareStatement(QUERY_ALL_STUDENT_STM_STRING);
            queryStudentByNumberStm = conn.prepareStatement(QUERY_STUDENT_BY_NUMBER_STM_STRING);
            // Course
            // add
            addCourseStm = conn.prepareStatement(ADD_COURSE_STM_STRING);
            // delete
            deleteCourseStm = conn.prepareStatement(DELETE_COURSE_STM_STRING);
            // query
            queryAllCourseStm = conn.prepareStatement(QUERY_ALL_COURSE_STM_STRING);
            queryCourseByNumberStm = conn.prepareStatement(QUERY_COURSE_BY_NUMBER_STM_STRING);
            queryCourseByInstructorStm = conn.prepareStatement(QUERY_COURSE_BY_INSTRUCTOR_STM_STRING);
            // Selection
            // add
            addSelectionStm = conn.prepareStatement(ADD_SELECTION_STM_STRING);
            deductCourseRemainSelectStm = conn.prepareStatement(DEDUCT_COURSE_REMAIN_SELECTION_STM_STRING);
            deductCourseRemainUpdateStm = conn.prepareStatement(DEDUCT_COURSE_REMAIN_UPDATE_STM_STRING);
            studentClasstimeSelectStm = conn.prepareStatement(STUDENT_CLASSTIME_SELECTION_STM_STRING);
            studentClasstimeUpdateStm = conn.prepareStatement(STUDENT_CLASSTIME_UPDATE_STM_STRING);
            // delete
            addCourseRemainSelectionStm = conn.prepareStatement(ADD_COURSE_REMAIN_SELECTION_STM_STRING);
            addCourseRemainUpdateStm = conn.prepareStatement(ADD_COURSE_REMAIN_UPDATE_STM_STRING);
            deleteSelectionByNumberStm = conn.prepareStatement(DELETE_SELECTION_BY_NUMBER_STM_STRING);
            deleteSelectionByCourseStm = conn.prepareStatement(DELETE_SELECTION_BY_COURSE_STM_STRING);
            // query
            queryAllSelectionStm = conn.prepareStatement(QUERY_ALL_SELECTION_STM_STRING);
            querySelectionByNumberStm = conn.prepareStatement(QUERY_SELECTION_BY_NUMBER_STM_STRING);

            querySelectionByCourseStm = conn.prepareStatement(QUERY_SELECTION_BY_COURSE_STM_STRING);
            querySelectionByStudentStm = conn.prepareStatement(QUERY_SELECTION_BY_STUDENT_STM_STRING);
            querySelectionByInstructorStm = conn.prepareStatement(QUERY_SELECTION_BY_INSTRUCTOR_STM_STRING);
            querySelectionByStudentAndInstructorStm = conn.prepareStatement(QUERY_SELECTION_BY_STUDENT_AND_INSTRUCTOR_STM_STRING);

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
     * @return True: Success, False: Failed
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
     * @return True: Success, False: Failed
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
            addStudentStm.setString(4, PrepareStatementUtils.STUDENT_DEFAULT_CLASSTIME);
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
                obj.put(MySqlConfig.SHOW_STUDENT_CLASSTIME, rs.getString(MySqlConfig.COLUMN_STUDENT_CLASSTIME));
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
                obj.put(MySqlConfig.SHOW_STUDENT_CLASSTIME, rs.getString(MySqlConfig.COLUMN_STUDENT_CLASSTIME));
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONObject();
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
     * Delete the course and course's selection data
     * 1. Get selection number array
     * 2. Delete selection by course number
     * 3. Remove student class time
     * 4. Delete course
     * @param courseNumber Course's number(Fixed to 5 char)
     * @return True: Success, False: Failed
     */
    public boolean deleteCourse(String courseNumber) {
        try {
            // 1. Get selection number array
            querySelectionByCourseStm.setString(1, courseNumber);
            ResultSet selectionRs = querySelectionByCourseStm.executeQuery();
            ArrayList<Integer> studentNumberArray = new ArrayList<>();
            while (selectionRs.next()) {
                studentNumberArray.add(selectionRs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
            }

            conn.setAutoCommit(false);

            // 2. Delete selection by course number
            deleteSelectionByCourseStm.setString(1, courseNumber);
            deleteSelectionByCourseStm.executeUpdate();

            // 3. Remove student class time
            for (int studentNumber : studentNumberArray) {
                if(!updateClasstime(courseNumber, studentNumber, UpdateMode.DELETE)) {
                    conn.rollback();
                    conn.commit();
                    return false;
                }
            }

            // 4. Delete course
            deleteCourseStm.setString(1, courseNumber);
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
     * Query course by instructor's number
     * @param instructorNumber Course's number
     * @return Course JSONArray
     */
    public JSONArray queryCourseByInstructor(int instructorNumber) {
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
     * Query  selection by selection number
     * @param selectionNumber Selection's number
     * @return Selection JSONObject
     */
    public JSONObject querySelectionByNumber(int selectionNumber) {
        try {
            querySelectionByNumberStm.setInt(1, selectionNumber);
            ResultSet rs = querySelectionByNumberStm.executeQuery();
            if (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_SELECTION_NUMBER, rs.getInt(MySqlConfig.COLUMN_SELECTION_NUMBER));
                obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
                obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
                return obj;
            }
            return new JSONObject();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }


    /**
     *  Verify course selection times do not conflict and merge selection class time
     * @param currentClassTime Current class time string array
     * @param selectionClassTime Selection class time string array
     * @return Conflict: empty string,  Success: New class time string
     */
    private String mergeClasstimeString(String[] currentClassTime, String[] selectionClassTime) {
        boolean[] classtimeOccupy = new boolean[8];
        for(String classTime : currentClassTime) {
            if(!classTime.equalsIgnoreCase(""))
                classtimeOccupy[Integer.valueOf(classTime) - 1] = true;
        }
        for(String classTime : selectionClassTime) {
            if(!classTime.equalsIgnoreCase("")) {
                if (classtimeOccupy[Integer.valueOf(classTime) - 1])
                    return "";
                else
                    classtimeOccupy[Integer.valueOf(classTime) - 1] = true;
            }
        }

        String newClassTime = "";
        for (int i = 0; i < classtimeOccupy.length; i++) {
            if(classtimeOccupy[i]) {
                if(newClassTime.equalsIgnoreCase(""))
                    newClassTime += String.valueOf(i+1);
                else
                    newClassTime += "," + String.valueOf(i+1);
            }
        }
        return newClassTime;
    }

    /**
     * Add a selection
     * Using locking reads to ...
     * 1. Add  class time
     * 2. Deduct course remain
     * 3. Insert a selection
     * https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
     * @param selectionNumber  Selection's number
     * @param courseNumber Course's number
     * @param studentNumber Student's number
     * @return True: Valid, False: Invalid
     */
    public boolean addSelection(int selectionNumber, int studentNumber, String courseNumber) {
        try {
            conn.setAutoCommit(false);

            // 1. Add class time
            if(!updateClasstime(courseNumber, studentNumber, UpdateMode.ADD)) {
                conn.commit();
                return false;
            }

            // 2. Deduct course remain
            deductCourseRemainSelectStm.setString(1, courseNumber);
            deductCourseRemainUpdateStm.setString(1, courseNumber);

            if (deductCourseRemainSelectStm.executeQuery().next()) {
                if (deductCourseRemainUpdateStm.executeUpdate() <= 0) {
                    conn.rollback();
                    conn.commit();
                    return false;
                }
            } else {
                conn.rollback();
                conn.commit();
                return false;
            }

            // 3. Insert selection
            if (selectionNumber > 0)
                addSelectionStm.setInt(1, selectionNumber);
            else
                addSelectionStm.setNull(1, 0);
            addSelectionStm.setString(2, courseNumber);
            addSelectionStm.setInt(3, studentNumber);
            if (addSelectionStm.executeUpdate() <= 0) {
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
                } catch (SQLException excep) {
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
     *  Remove selection class time from current class time string
     * @param currentClassTime Current class time string array
     * @param selectionClassTime Selection class time string array
     * @return Conflict: empty string,  Success: New class time string
     */
    private String removeClasstimeString(String[] currentClassTime, String[] selectionClassTime) {
        boolean[] classtimeOccupy = new boolean[8];
        for(String classTime : currentClassTime) {
            if(!classTime.equalsIgnoreCase(""))
                classtimeOccupy[Integer.valueOf(classTime) - 1] = true;
        }
        for(String classTime : selectionClassTime) {
            if(!classTime.equalsIgnoreCase("")) {
                classtimeOccupy[Integer.valueOf(classTime) - 1] = false;
            }
        }

        String newClassTime = "";
        for (int i = 0; i < classtimeOccupy.length; i++) {
            if(classtimeOccupy[i]) {
                if(newClassTime.equalsIgnoreCase(""))
                    newClassTime += String.valueOf(i+1);
                else
                    newClassTime += "," + String.valueOf(i+1);
            }
        }
        return newClassTime;
    }

    /**
     * Update class time
     * @param courseNumber Course number you would like to add or drop
     * @param studentNumber Student number
     * @param mode Add or Delete mode
     * @return True: Success, False: Failed
     */
    private boolean updateClasstime(String courseNumber, int studentNumber, UpdateMode mode) {
        try {
            // Get selection class time
            JSONObject selectionCourseObj = queryCourseByNumber(courseNumber);
            int selectiontWeekday = selectionCourseObj.getInt(MySqlConfig.SHOW_COURSE_WEEKDAY);
            String[] selectionClassTime = selectionCourseObj.getString(MySqlConfig.SHOW_COURSE_CLASSTIME).split(",");

            studentClasstimeSelectStm.setInt(1, studentNumber);
            ResultSet rs = studentClasstimeSelectStm.executeQuery();
            if (rs.next()) {
                // Get current class time
                JSONObject currentClasstimeObj = new JSONObject(rs.getString(MySqlConfig.COLUMN_STUDENT_CLASSTIME));
                String[] currentClassTime = currentClasstimeObj.getString(String.valueOf(selectiontWeekday)).split(",");
                String newClassTimeString = "";
                // Update class time string
                switch (mode) {
                    case ADD:
                        // Merge current and selection class time string
                        newClassTimeString = mergeClasstimeString(currentClassTime, selectionClassTime);
                        break;
                    case DELETE:
                        // Remove selection class time from current class time string
                        newClassTimeString = removeClasstimeString(currentClassTime, selectionClassTime);
                        break;
                }
                // Update new class time
                currentClasstimeObj.put(String.valueOf(selectiontWeekday), newClassTimeString);
                studentClasstimeUpdateStm.setString(1, currentClasstimeObj.toString());
                studentClasstimeUpdateStm.setInt(2, studentNumber);
                return studentClasstimeUpdateStm.executeUpdate() >= 0;
            } else {
                return false;
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch(SQLException excep) {
                    excep.printStackTrace();
                }
            }
            return false;
        }
    }

    /**
     * Delete selection by selection number
     * 1. Get course and student number by selection number
     * Using locking read to...
     * 2. Delete selection by selection number
     * 3. Add course remain
     * 4. Remove class time
     * https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
     * @param selectionNumber Selection's number
     * @return True: Valid, False: Invalid
     */
    public boolean deleteSelectionByNumber(int selectionNumber) {
        try {
            // 1. Get course and student number by selection number
            JSONObject obj = querySelectionByNumber(selectionNumber);
            if(obj.isEmpty()) {
                return false;
            }
            String courseNumber = obj.getString(MySqlConfig.SHOW_COURSE_NUMBER);
            int studentNumber = obj.getInt(MySqlConfig.SHOW_STUDENT_NUMBER);

            conn.setAutoCommit(false);

            // 2. Delete selection by selection number
            deleteSelectionByNumberStm.setInt(1, selectionNumber);
            if(deleteSelectionByNumberStm.executeUpdate() <= 0) {
                conn.commit();
                return false;
            }

            // 3. Add course remain
            addCourseRemainSelectionStm.setString(1, courseNumber);
            addCourseRemainUpdateStm.setString(1, courseNumber);
            if(addCourseRemainSelectionStm.executeQuery().next()) {
                if(addCourseRemainUpdateStm.executeUpdate() <= 0) {
                    conn.rollback();
                    conn.commit();
                    return false;
                }
            }

            // 4. Remove class time
            if(!updateClasstime(courseNumber, studentNumber, UpdateMode.DELETE)) {
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


    private JSONObject getCourseSelectionJson(ResultSet rs) {
        try {
            JSONObject obj = new JSONObject();
            obj.put(MySqlConfig.SHOW_SELECTION_NUMBER, rs.getInt(MySqlConfig.COLUMN_SELECTION_NUMBER));
            obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
            obj.put(MySqlConfig.SHOW_STUDENT_NAME, rs.getString(MySqlConfig.COLUMN_STUDENT_NAME));
            obj.put(MySqlConfig.SHOW_STUDENT_GENDER, rs.getString(MySqlConfig.COLUMN_STUDENT_GENDER));
            obj.put(MySqlConfig.SHOW_STUDENT_CLASSTIME, rs.getString(MySqlConfig.COLUMN_STUDENT_CLASSTIME));
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
    public JSONArray querySelectionByStudent(int studentNumber) {
        try {
            querySelectionByStudentStm.setInt(1, studentNumber);
            ResultSet rs = querySelectionByStudentStm.executeQuery();
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
    public JSONArray querySelectionByInstructor(int instructorNumber) {
        try {
            querySelectionByInstructorStm.setInt(1, instructorNumber);
            ResultSet rs = querySelectionByInstructorStm.executeQuery();
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
     * Query selection by student and instructor's number
     * @param instructorNumber Instructor's number
     * @return Detail course selection information
     */
    public JSONArray querySelectionByStudentAndInstructor(int studentNumber, int instructorNumber) {
        try {
            querySelectionByStudentAndInstructorStm.setInt(1, studentNumber);
            querySelectionByStudentAndInstructorStm.setInt(2, instructorNumber);
            ResultSet rs = querySelectionByStudentAndInstructorStm.executeQuery();
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

}
