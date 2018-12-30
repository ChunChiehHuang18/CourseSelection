package server.mysql.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static server.mysql.helper.PrepareStatementUtils.*;

/**
 * Providing instructor related JDBC API
 */
public class InstructorDbHelper implements IDbHelper {

    // add
    private PreparedStatement addInstructorStm = null;
    // query
    private PreparedStatement queryAllInstructorStm = null;
    private PreparedStatement queryInstructorByNumberStm = null;


    InstructorDbHelper(Connection conn) {
        try {

            // add
            addInstructorStm = conn.prepareStatement(ADD_INSTRUCTOR_STM_STRING);
            // query
            queryAllInstructorStm = conn.prepareStatement(QUERY_ALL_INSTRUCTOR_STM_STRING);
            queryInstructorByNumberStm = conn.prepareStatement(QUERY_INSTRUCTOR_BY_NUMBER_STM_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query all instructor list
     * @return Instructor JSONArray
     */
    @Override
    public JSONArray queryAll() {
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
     * @param number Instructor's number
     * @return Instructor JSONObject
     */
    @Override
    public JSONObject queryByNumber(String number) {
        try {
            queryInstructorByNumberStm.setInt(1, Integer.valueOf(number));
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
     * Add a instructor into MySql
     * @param instructorNumber Instructor's number
     * @param instructorName Instructor's name
     * @param office Instructor's office (can be null)
     * @return True: Success, False: Failed
     */
    public boolean add(int instructorNumber, String instructorName, String office) {
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

    @Override
    public boolean delete(String number) {
        return false;
    }
}
