package air.database;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Response;

/**
 * Created by mruzman on 15.11.2017..
 */

public interface Services {

    byte[] queryManipulation(String query) throws IOException;

    byte[] getLogin(String username, String password) throws IOException, JSONException;

    byte[] getIfExistsUser(String username, String email) throws IOException;

    byte[] insertNewUser(String...strings) throws IOException;

    byte[] getIfExistsApp(String appName) throws IOException;

    byte[] insertNewApp(String app) throws IOException;

    List<String> insertNewRecivedBug(Email email, App app, int userID) throws IOException, JSONException;

    byte[] getUserPriority(Integer userID,Integer appID) throws IOException;

    byte[] insertRespond(Response response) throws IOException;

    byte[] getAllResponsesForProblem(String emailID) throws IOException;
}
