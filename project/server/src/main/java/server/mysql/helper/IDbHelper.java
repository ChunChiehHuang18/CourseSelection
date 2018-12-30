package server.mysql.helper;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Db helper interface
 */
public interface IDbHelper {
    JSONArray queryAll();
    JSONObject queryByNumber(String number);
    boolean delete(String number);
}
