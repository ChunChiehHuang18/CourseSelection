package server.mysql.dbhelper;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Db helper interface
 */
public interface IDbHelper {
    JSONArray queryAll();
    JSONObject queryByNumber(String number);
    boolean delete(String number);
    boolean validDeleteData(String number);
}
