package air.database;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Email;

/**
 * Created by mruzman on 15.11.2017..
 */

public interface Services {

    byte[] queryManipulation(String query) throws IOException;

    Integer insertQueryManipulation(String query) throws IOException;

    byte[] getLogin(String username, String password) throws IOException, JSONException;

    byte[] getAdminApps(Integer userId) throws  IOException;

    byte[] getIfExistsUser(String username, String email) throws IOException;

    byte[] insertNewUser(String...strings) throws IOException;

    byte[] getIfExistsApp(String appName) throws IOException;

    Integer insertNewApp(String app, String userID) throws IOException;

    List<String> insertNewRecivedBug(Email email, App app, int userID) throws IOException, JSONException;

    byte[] getUserPriority(Integer userID,Integer appID) throws IOException;

    byte[] getAllResponsesForProblem(String emailID) throws IOException;
}
