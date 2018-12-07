package server.mysql.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.Arrays;

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
    private PreparedStatement queryStudentClasstimeStm = null;
    private PreparedStatement querySelectionDuplicateStm = null;
    // delete
    private PreparedStatement addCourseRemainSelectionStm = null;
    private PreparedStatement addCourseRemainUpdateStm = null;
    private PreparedStatement deleteSelectionByNumberStm = null;
    private PreparedStatement deleteSelectionByCourseStm = null;
    // query
    private PreparedStatement queryAllSelectionStm = null;
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
            queryStudentClasstimeStm = conn.prepareStatement(QUERY_STUDENT_CLASSTIME_STM_STRING);
            querySelectionDuplicateStm = conn.prepareStatement(QUERY_SELECTION_DUPLICATE_STM_STRING);
            // delete
            addCourseRemainSelectionStm = conn.prepareStatement(ADD_COURSE_REMAIN_SELECTION_STM_STRING);
            addCourseRemainUpdateStm = conn.prepareStatement(ADD_COURSE_REMAIN_UPDATE_STM_STRING);
            deleteSelectionByNumberStm = conn.prepareStatement(DELETE_SELECTION_BY_NUMBER_STM_STRING);
            deleteSelectionByCourseStm = conn.prepareStatement(DELETE_SELECTION_BY_COURSE_STM_STRING);
            // query
            queryAllSelectionStm = conn.prepareStatement(QUERY_ALL_SELECTION_STM_STRING);
            querySelectionByNumberStm = conn.prepareStatement(QUERY_SELECTION_BY_NUMBER_STM_STRING);

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
     * @param courseNumber Course's number(Fixed to 5 char)
     * @return True: Success, False: Failed
     */
    public boolean deleteCourse(String courseNumber) {
        try {
            conn.setAutoCommit(false);

            // delete selection by course
            deleteSelectionByCourse(courseNumber);

            // delete course
            deleteCourseStm.setString(1, courseNumber);
            int affectedRow = deleteCourseStm.executeUpdate();


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
     * Select a Course and insert into MySql
     * @param selectionNumber Selection's number
     * @param studentNumber Student's number
     * @param courseNumber Course's number
     * @return True: Success, False: Failed
     */
    public boolean addSelection(int selectionNumber, int studentNumber, String courseNumber) {
        try {
            if(selectionNumber > 0)
                addSelectionStm.setInt(1, selectionNumber);
            else
                addSelectionStm.setNull(1, 0);
            addSelectionStm.setString(2, courseNumber);
            addSelectionStm.setInt(3, studentNumber);
            addSelectionStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Delete  selection by selection number
     * @param selectionNumber Selection's number
     * @return True: Success, False: Failed
     */
    public boolean deleteSelectionByNumber(int selectionNumber) {
        try {
            deleteSelectionByNumberStm.setInt(1, selectionNumber);
            deleteSelectionByNumberStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete  selection by course number
     * @param courseNumber Course's number
     * @return True: Success, False: Failed
     */
    private boolean deleteSelectionByCourse(String courseNumber) {
        try {
            deleteSelectionByCourseStm.setString(1, courseNumber);
            deleteSelectionByCourseStm.executeUpdate();
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
     * Using locking reads to ...
     * 1. Check duplicate selection
     * 2. Verify course selection times do not conflict
     * 3. Deduct course remain
     * https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
     * @param courseNumber Course's number
     * @param studentNumber Student's number
     * @return True: Valid, False: Invalid
     */
    public boolean validSelectionData(int studentNumber, String courseNumber) {
        try {
            conn.setAutoCommit(false);

            // 1. Check duplicate selection
            querySelectionDuplicateStm.setInt(1, studentNumber);
            querySelectionDuplicateStm.setString(2, courseNumber);
            ResultSet rs = querySelectionDuplicateStm.executeQuery();
            if(rs.next()) {
                if (rs.getInt(1) > 0) {
                    conn.commit();
                    return false;
                }
            }

            // 2. Verify course selection times do not conflict
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

            // 3. Deduct course remain
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
     * Using locking reads to ...
     * 1. Get Student and Course's number
     * 2. Check selection exist
     * 3. Add course remain
     * https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
     * @param selectionNumber Selection's number
     * @return True: Valid, False: Invalid
     */
    public boolean validDeleteByNumberSelectionData(int selectionNumber) {
        try {
            conn.setAutoCommit(false);

            // 1.  Get student and instructor's number
            JSONObject obj = querySelectionByNumber(selectionNumber);
            if(obj.isEmpty()) {
                conn.commit();
                return false;
            }
            int studentNumber = obj.getInt(MySqlConfig.SHOW_STUDENT_NUMBER);
            String courseNumber = obj.getString(MySqlConfig.SHOW_COURSE_NUMBER);

            // 2. Check  selection exist
            querySelectionDuplicateStm.setInt(1, studentNumber);
            querySelectionDuplicateStm.setString(2, courseNumber);
            ResultSet rs = querySelectionDuplicateStm.executeQuery();
            if(rs.next()) {
                if (rs.getInt(1) == 0) {
                    conn.commit();
                    return false;
                }
            } else {
                conn.commit();
                return false;
            }

            // 3. Add course remain
            // Try to add course remain and check affect row number
            addCourseRemainSelectionStm.setString(1, courseNumber);
            addCourseRemainUpdateStm.setString(1, courseNumber);
            int affectedRow = 0;
            if(addCourseRemainSelectionStm.executeQuery().next()) {
                affectedRow =  addCourseRemainUpdateStm.executeUpdate() ;
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
