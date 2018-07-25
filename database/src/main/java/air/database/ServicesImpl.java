package air.database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Response;
import air.database.helper.Constants;

/**
 * Created by mruzman on 15.11.2017..
 */

public class ServicesImpl implements Services {
    private static String TAG = "Services";

    @Override
    public byte[] queryManipulation(String query) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String createURL = Constants.URL + "?q="+query;
        Log.i(TAG, query);
        URL url = new URL(createURL);
        Log.i(TAG, createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = null;
        try{
            in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + createURL);
            }
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, byteRead);
            }
            out.close();
        } finally {
            connection.disconnect();
        }
        return out.toByteArray();
    }

    @Override
    public byte[] getLogin(String username, String password) throws IOException, JSONException {
        String query = "SELECT * FROM user ";
        query += "WHERE username='" + username + "' ";
        query += "AND password='" + password + "'";
        return queryManipulation(query);

    }

    @Override
    public byte[] getIfExistsUser(String username, String email) throws IOException {
        String query = "SELECT * FROM user ";
        query += "WHERE username='" + username + "' ";
        query += "OR email='" + email + "'";
        return queryManipulation(query);
    }

    @Override
    public byte[] insertNewUser(String... strings) throws IOException {
        String firstName = strings[0];
        String lastName = strings[1];
        String username = strings[2];
        String email = strings[3];
        String password = strings[4];
        String gmailPassword = strings[5];
        String query = "INSERT INTO user(user_id,first_name,last_name,";
        query += "username,email,password,type, gmail_password) VALUES( default,'";
        query += firstName + "','" + lastName + "','";
        query += username + "','" + email + "','";
        query += password + "','USER'" + ", '" + gmailPassword + "')";
        return queryManipulation(query);
    }

    @Override
    public byte[] getIfExistsApp(String appName) throws IOException {
        String query = "SELECT * FROM application WHERE name='" + appName + "'";
        return queryManipulation(query);
    }


    public byte[] getIfExistsAppUser(String appID, String userID) throws IOException {
        String query = "SELECT * FROM user_application WHERE application_id='" + appID + "' AND user_id = '"+userID+"'";
        String createURL = Constants.URL + "?q=" + query;
        return queryManipulation(query);
    }


    @Override
    public byte[] insertNewApp(String app) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO application(name) ";
        query += "VALUES('" + app + "')";
        return queryManipulation(query);
    }


    public byte[] insertNewAppUser(String appID, String userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO user_application(application_id, user_id) ";
        query += "VALUES('" + appID + "', '"+ userID +"')";
        return queryManipulation(query);
    }

    public byte[] deleteAppUser(String appID, String userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "DELETE FROM user_application WHERE application_id = '"+appID+"'AND user_id = '"+userID+"' ";
        return queryManipulation(query);
    }

    private int emailID;
    private int appID;

    //TODO popraviti
    public List<String> insertNewRecivedBug(Email mail, App app, int userID) throws IOException, JSONException {
//        List<String> newValuesToReturn = new ArrayList<String>();
//        emailID = 0;
//        String stringAppID = findAppId(app.getName());
//        JSONObject jsonObject = new JSONObject(stringAppID);
//        JSONArray jsonArray = jsonObject.getJSONArray("records");
//        if (stringAppID.length() > 0 || !stringAppID.isEmpty()) {
//            JSONObject object = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
//            appID = object.getInt("application_id");
//            String eventIDString = checkIfEventExists(event.getName(), event.getDescription(), appID);
//            jsonObject = new JSONObject(eventIDString);
//            jsonArray = jsonObject.getJSONArray("records");
//            JSONObject object1;
//            if (jsonArray.length() == 0) {
//                eventIDString = insertEvent(event.getName(), event.getDescription(), appID);
//                object1 = new JSONObject(eventIDString);
//                jsonArray = object1.getJSONArray("records");
//            }
//            object1 = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
//            eventID = object1.getInt("event_id");
//            String emailIDString = checkAndGetEmail(mail.getDescription(), mail.getTimeEventOccured(), Constants.STATUS_UNSOLVED, eventID, userID);
//            jsonObject = new JSONObject(emailIDString);
//            jsonArray = jsonObject.getJSONArray("records");
//            if (jsonArray.length() == 0) {
//                emailIDString = insertNewMail(mail.getDescription(), mail.getTimeEventOccured(), Constants.STATUS_UNSOLVED, eventID, userID);
//                object1 = new JSONObject(emailIDString);
//                jsonArray = object1.getJSONArray("records");
//            }
//            object1 = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
//            emailID = object1.getInt("email_id");
//            newValuesToReturn.add(String.valueOf(appID));
//            newValuesToReturn.add(String.valueOf(eventID));
//            newValuesToReturn.add(String.valueOf(emailID));
//        } else {
//            Log.i(TAG, "Ne postoji aplikacija");
//        }
//        return newValuesToReturn;
        return null;
    }

    private String  insertNewMail(String s, Timestamp s1, String statusUnsolved, int eventID, int userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO email(description, time_event_occured, status, user_id, event_id) ";
        query += "VALUES('" + s + "','" + s1 + "','" + statusUnsolved + "','" + userID + "','" + eventID + "')";
        queryManipulation(query);
        return checkAndGetEmail(s, s1, statusUnsolved, eventID, userID);
    }

    private String checkAndGetEmail(String s, Timestamp s1, String statusUnsolved, int eventID, int userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "SELECT email_id FROM email ";
        query += "WHERE description='" + s + "' AND time_event_occured = '" + s1 +
                "' AND user_id ='" + userID + "' AND event_id ='" + eventID + "' LIMIT 1;";
        return queryManipulation(query).toString();
    }


    public byte[] getUsers() throws IOException {
        ByteArrayOutputStream out;
        String query = "SELECT * FROM user";
        String createURL = Constants.URL + "?q=" + query;
        return queryManipulation(query);
    }

    public byte[] getApps() throws IOException {
        String query = "SELECT * FROM application";
        return queryManipulation(query);
    }

    public byte[] getEmails(String appID) throws IOException {
        String query = "select e.* from email as e inner join `event` as ev on e.event_id = ev.event_id WHERE ev.application_id = '"+appID+"' ";
         return queryManipulation(query);
    }

    public byte[] getAppUsers(int appID) throws IOException {
        String query = "select u.*, case when ua.application_id is null then 0 else ua.application_id end as application_id from user as u left join user_application as ua on ua.user_id = u.user_id and ua.application_id = " + appID;
        return queryManipulation(query);
    }

    public byte[] getUserApps(String userID) throws IOException {
        String query = "SELECT a.* FROM application AS a INNER JOIN user_application AS ua ON a.application_id = ua.application_id WHERE ua.user_id = '" + userID + "'";
        return queryManipulation(query);
    }

    @Override
    public byte[] getUserPriority(Integer userID, Integer appID) throws IOException {
        String query = "SELECT priority FROM user_application ";
        query += "WHERE user_id=" + userID + " AND application_id=" + appID + ";";
        return queryManipulation(query);
    }

    @Override
    public byte[] insertRespond(Response response) throws IOException {
        String query = "INSERT INTO response(email_id,user_id,response,date_respond) ";
        query += "VALUES ('" + response.getEmailId() + "','" + response.getUserId() +
                "','" + response.getResponse() + "',"+new Date()+");";
        return queryManipulation(query);
    }

    @Override
    public byte[] getAllResponsesForProblem(String emailID) throws IOException {
        //TODO vratiti
        String query = "SELECT * FROM response";
        query += "WHERE  email=" + emailID + " ORDER BY date_respond;";
        return queryManipulation(query);
    }


    //TODO preformulirati
    private String insertEvent(String name, String desc, int appID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO event(name,description,application_id) ";
        query += "VALUES('" + name + "','" + desc + "','" + appID + "')";
        queryManipulation(query);
        return "";
    }

    private byte[] findAppId(String s) throws IOException {
        String query = "SELECT application_id FROM application ";
        query += "WHERE name='" + s + "';";
        return queryManipulation(query);
    }
}

