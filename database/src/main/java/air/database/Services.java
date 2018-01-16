package air.database;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Event;

/**
 * Created by mruzman on 15.11.2017..
 */

public interface Services {
    byte[] getLogin(String username, String password) throws IOException, JSONException;

    byte[] getIfExistsUser(String username, String email) throws IOException;

    Integer insertNewUser(String...strings) throws IOException;

    Integer getIfExistsApp(String appName) throws IOException;

    Integer insertNewApp(String app) throws IOException;

    List<String> insertNewRecivedBug(Event event,Email email, App app, int userID) throws IOException, JSONException;

    String getUserPriority(Integer userID,Integer appID) throws IOException;
}
