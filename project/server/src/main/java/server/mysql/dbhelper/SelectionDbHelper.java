package server.mysql.dbhelper;

import org.json.JSONArray;
import org.json.JSONObject;
import server.mysql.utils.MySqlConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static server.mysql.utils.PrepareStatementUtils.*;

/**
 * Providing selection related JDBC API
 */
public class SelectionDbHelper implements IDbHelper {

    private Connection conn;
    private ClasstimeDbHelper classtimeDbHelper;

    // add
    private PreparedStatement deductCourseRemainSelectStm = null;
    private PreparedStatement deductCourseRemainUpdateStm = null;
    private PreparedStatement addSelectionStm = null;
    // delete
    private PreparedStatement addCourseRemainSelectionStm = null;
    private PreparedStatement addCourseRemainUpdateStm = null;
    private PreparedStatement deleteSelectionByNumberStm = null;

    // query
    private PreparedStatement queryAllSelectionStm = null;
    private PreparedStatement querySelectionByNumberStm = null;
    private PreparedStatement querySelectionByStudentStm = null;
    private PreparedStatement querySelectionByInstructorStm = null;
    private PreparedStatement querySelectionByStudentAndInstructorStm = null;

    public SelectionDbHelper(Connection conn) {
        try {

            this.conn = conn;

            // add
            addSelectionStm = conn.prepareStatement(Selection.ADD);
            deductCourseRemainSelectStm = conn.prepareStatement(Course.DEDUCT_COURSE_REMAIN_SELECTION);
            deductCourseRemainUpdateStm = conn.prepareStatement(Course.DEDUCT_COURSE_REMAIN_UPDATE);
            // delete
            addCourseRemainSelectionStm = conn.prepareStatement(Course.ADD_COURSE_REMAIN_SELECTION);
            addCourseRemainUpdateStm = conn.prepareStatement(Course.ADD_COURSE_REMAIN_UPDATE);
            deleteSelectionByNumberStm = conn.prepareStatement(Selection.DELETE_BY_NUMBER);
            // query
            queryAllSelectionStm = conn.prepareStatement(Selection.QUERY_ALL);
            querySelectionByNumberStm = conn.prepareStatement(Selection.QUERY_BY_NUMBER);
            querySelectionByStudentStm = conn.prepareStatement(Selection.QUERY_BY_STUDENT);
            querySelectionByInstructorStm = conn.prepareStatement(Selection.QUERY_BY_INSTRUCTOR);
            querySelectionByStudentAndInstructorStm = conn.prepareStatement(Selection.QUERY_BY_STUDENT_AND_INSTRUCTOR);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setClasstimeDbHelper(ClasstimeDbHelper dbHelper) {
        this.classtimeDbHelper = dbHelper;
    }

    /**
     * Query all selection list
     * @return Selection JSONArray
     */
    @Override
    public JSONArray queryAll() {
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
     * @param number Selection's number
     * @return Selection JSONObject
     */
    @Override
    public JSONObject queryByNumber(String number) {
        try {
            querySelectionByNumberStm.setInt(1, Integer.valueOf(number));
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
     * Query selection by student's number
     * @param studentNumber Student's number
     * @return Detail course selection information
     */
    public JSONArray queryByStudent(int studentNumber) {
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
    public JSONArray queryByInstructor(int instructorNumber) {
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
    public JSONArray queryByStudentAndInstructor(int studentNumber, int instructorNumber) {
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
    public boolean add(int selectionNumber, int studentNumber, String courseNumber) {
        try {
            conn.setAutoCommit(false);

            // 1. Add class time
            if(classtimeDbHelper == null ||
                    !classtimeDbHelper.updateClasstime(courseNumber, studentNumber, ClasstimeDbHelper.UpdateMode.ADD)) {
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
     * Delete selection by selection number
     * 1. Get course and student number by selection number
     * Using locking read to...
     * 2. Delete selection by selection number
     * 3. Add course remain
     * 4. Remove class time
     * https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
     * @param number Selection's number
     * @return True: Valid, False: Invalid
     */
    @Override
    public boolean delete(String number) {
        try {
            // 1. Get course and student number by selection number
            JSONObject obj = queryByNumber(number);
            if(obj.isEmpty()) {
                return false;
            }
            String courseNumber = obj.getString(MySqlConfig.SHOW_COURSE_NUMBER);
            int studentNumber = obj.getInt(MySqlConfig.SHOW_STUDENT_NUMBER);

            conn.setAutoCommit(false);

            // 2. Delete selection by selection number
            deleteSelectionByNumberStm.setInt(1, Integer.valueOf(number));
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
            if(classtimeDbHelper == null ||
                    !classtimeDbHelper.updateClasstime(courseNumber, studentNumber, ClasstimeDbHelper.UpdateMode.DELETE)) {
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
     * Check basic selection number is legal
     * @param number Selection's number
     * @return True: Valid, False: Invalid
     */
    @Override
    public boolean validDeleteData(String number) {
        return Integer.valueOf(number) > 0;
    }
}
