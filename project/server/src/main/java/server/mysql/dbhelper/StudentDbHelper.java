package server.mysql.dbhelper;

import org.json.JSONArray;
import org.json.JSONObject;
import server.mysql.utils.MySqlConfig;

import java.sql.*;

import static server.mysql.utils.PrepareStatementUtils.*;

/**
 * Providing student related JDBC API
 */
public class StudentDbHelper implements IDbHelper {

    // add
    private PreparedStatement addStudentStm = null;
    // query
    private PreparedStatement queryAllStudentStm = null;
    private PreparedStatement queryStudentByNumberStm = null;

    public StudentDbHelper(Connection conn) {
        try {

            // add
            addStudentStm = conn.prepareStatement(Student.ADD);
            // query
            queryAllStudentStm = conn.prepareStatement(Student.QUERY_ALL);
            queryStudentByNumberStm = conn.prepareStatement(Student.QUERY_BY_NUMBER);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }


    /**
     * Query all student
     * @return Student JSONArray
     */
    @Override
    public JSONArray queryAll() {
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
     * @param number Student's number
     * @return Student JSONObject
     */
    @Override
    public JSONObject queryByNumber(String number) {
        try {
            queryStudentByNumberStm.setInt(1, Integer.valueOf(number));
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
     * Add a student into MySql
     * @param studentNumber Student's number
     * @param studentName Student's name
     * @param gender Student's gender(Male, Female, Bisexual) (can be null)
     * @return True: Success, False: Failed
     */
    public boolean add(int studentNumber, String studentName, String gender) {
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
            addStudentStm.setString(4, Student.STUDENT_DEFAULT_CLASSTIME);
            addStudentStm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String number) {
        return false;
    }

    @Override
    public boolean validDeleteData(String number) {
        return false;
    }
}
