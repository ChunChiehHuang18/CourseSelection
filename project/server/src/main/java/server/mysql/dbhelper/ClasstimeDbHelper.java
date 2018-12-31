package server.mysql.dbhelper;

import org.json.JSONObject;
import server.mysql.utils.MySqlConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.mysql.utils.PrepareStatementUtils.*;

/**
 * Providing update class time JDBC API
 */
public class ClasstimeDbHelper {

    public enum UpdateMode {
        ADD, DELETE
    }

    private Connection conn;

    private PreparedStatement studentClasstimeSelectStm = null;
    private PreparedStatement studentClasstimeUpdateStm = null;

    private CourseDbHelper courseDbHelper;

    public ClasstimeDbHelper(Connection conn, CourseDbHelper courseDbHelper) {
        try {
            this.conn = conn;

            studentClasstimeSelectStm = conn.prepareStatement(Student.STUDENT_CLASSTIME_SELECTION);
            studentClasstimeUpdateStm = conn.prepareStatement(Student.STUDENT_CLASSTIME_UPDATE);

            this.courseDbHelper = courseDbHelper;

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public boolean updateClasstime(String courseNumber, int studentNumber, UpdateMode mode) {
        try {
            // Get selection class time
            JSONObject selectionCourseObj = courseDbHelper.queryByNumber(courseNumber);
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
                        if(newClassTimeString.equalsIgnoreCase(""))
                            return false;
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
}
