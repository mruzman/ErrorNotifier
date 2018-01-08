package air.database;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by mruzman on 15.11.2017..
 */

public interface Services {
    byte[] getLogin(String username, String password) throws IOException, JSONException;

    byte[] getIfExistsUser(String username, String email) throws IOException;

    Integer insertNewUser(String...strings) throws IOException;

    Integer getIfExistsApp(String appName) throws IOException;

    Integer insertNewApp(String app) throws IOException;

    List<String> insertNewRecivedBug(List<String> strings, int userID) throws IOException, JSONException;
}
