package air.database;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import air.database.helper.Constants;

/**
 * Created by mruzman on 15.11.2017..
 */

public class ServicesImpl implements Services {
    private static String TAG = "Services";

    @Override
    public byte[] getLogin(String username, String password) throws IOException, JSONException {
        String query = "SELECT * FROM user ";
        query += "WHERE username='" + username + "' ";
        query += "AND password='" + password + "'";
        String createURL = Constants.URL + "?q=" + query;
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    @Override
    public byte[] getIfExistsUser(String username, String email) throws IOException {
        String query = "SELECT * FROM user ";
        query += "WHERE username='" + username + "' ";
        query += "OR email='" + email + "'";
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, query.toString());
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    @Override
    public Integer insertNewUser(String... strings) throws IOException {
        ByteArrayOutputStream out;
        String firstName = strings[0];
        String lastName = strings[1];
        String username = strings[2];
        String email = strings[3];
        String password = strings[4];
        String query = "INSERT INTO user(user_id,first_name,last_name,";
        query += "username,email,password,type) VALUES( default,'";
        query += firstName + "','" + lastName + "','";
        query += username + "','" + email + "','";
        query += password + "','USER')";
        Log.i(TAG, query.toString());
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL.toString());
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
        } finally {
            connection.disconnect();
        }
        return Integer.valueOf(out.toString());
    }

    @Override
    public Integer getIfExistsApp(String appName) throws IOException {
        String query = "SELECT * FROM application WHERE name='" + appName + "'";
        String createURL = Constants.URL + "?q=" + query;
        URL url = new URL(createURL);
        Log.i(TAG, createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + createURL);
            }
            byte[] buffer = new byte[1024];
            if (in.read(buffer) > 0) {
                connection.disconnect();
                return Constants.DATA_EXISTS_IN_DATABASE; //ako postoje podaci unutra vrati 0
            } else {
                connection.disconnect();
                return Constants.DATA_DOESNT_EXISTS_IN_DATABASE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.ERROR;
        }
    }


    @Override
    public Integer insertNewApp(String app) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO application(name) ";
        query += "VALUES('" + app + "')";
        Log.i(TAG, query);
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
        } finally {
            connection.disconnect();
        }
        return Integer.valueOf(out.toString());
    }

    private int eventID;
    private int emailID;
    //TODO Dodati ova sranja do kraja
    @Override
    public List<String> insertNewRecivedBug(List<String> strings, int userID) throws IOException, JSONException {
        List<String> newValuesToReturn = new ArrayList<String>();
        eventID = 0;
        emailID =0;
        String stringAppID = findAppId(strings.get(1));
        Log.i("appID", String.valueOf(stringAppID));
        if (stringAppID.length() > 0 || !stringAppID.isEmpty()) {
            JSONObject object = new JSONObject(stringAppID);
            int appID = object.getInt("application_id");
            Log.i("APPID", String.valueOf(appID));
            String eventIDString = checkIfEventExists(strings.get(0), strings.get(2), appID);
            if (eventIDString.length() == 0 || eventIDString.isEmpty()) {
                eventIDString = insertEvent(strings.get(0), strings.get(2), appID);
            }
            JSONObject object1 = new JSONObject(eventIDString);
            eventID = object1.getInt("event_id");
            String emailIDString = checkAndGetEmail(strings.get(2),strings.get(3),strings.get(4),Constants.STATUS_UNSOLVED,eventID,userID);
            if(emailIDString.length() == 0 || emailIDString.isEmpty()){ //strigs 2 - desc, strings 3- time error, strings 4 - time warning
                emailIDString = insertNewMail(strings.get(2),strings.get(3),strings.get(4),Constants.STATUS_UNSOLVED,eventID,userID);
            }
            object1 = new JSONObject(emailIDString);
            emailID = object1.getInt("email_id");
            newValuesToReturn.add(String.valueOf(appID));
            newValuesToReturn.add(String.valueOf(eventID));
            newValuesToReturn.add(String.valueOf(emailID));
        } else {
            Log.i(TAG, "Ne postoji aplikacija");
            //vratiti da nepostoji aplikacija -- treba unesti aplikaciju.
        }
        return newValuesToReturn;
    }

    private String insertNewMail(String s, String s1, String s2, String statusUnsolved, int eventID, int userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO email(description, "+(s1 !="" ? "time_error " : "time_warning ") + ", status, user_id, event_id) ";
        query += "VALUES('" + s + "','" + (s1 != "" ? s1 : s2) + "','" + statusUnsolved + "','"+userID+"','"+eventID+"')";
        Log.i(TAG, query);
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
        } finally {
            connection.disconnect();
        }
        return checkAndGetEmail(s,s1,s2,statusUnsolved,eventID,userID);
    }

    private String checkAndGetEmail(String s, String s1, String s2, String statusUnsolved, int eventID, int userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "SELECT email_id FROM email ";
        query += "WHERE description='" + s + "' AND "+(s1 != "" ? "time_error = '" + s1 : "time_warning = '"+s2) +
                "' AND user_id ='"+userID+"' AND event_id ='"+eventID+"' LIMIT 1;";
        Log.i("QUERY", query);
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
        } finally {
            connection.disconnect();
        }
        return out.toString();
    }

    @Override
    public String getUserPriority(Integer userID, Integer appID) throws IOException {
        ByteArrayOutputStream out;
        String query = "SELECT priority FROM user_application ";
        query += "WHERE user_id=" + userID + " AND application_id=" + appID + ";";
        Log.i(TAG, query);
        String createURL = Constants.URL + "?q=" + query;
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
            return out.toString();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String insertEvent(String name, String desc, int appID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO event(name,description,application_id) ";
        query += "VALUES('" + name + "','" + desc + "','" + appID + "')";
        Log.i(TAG, query);
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
        } finally {
            connection.disconnect();
        }
        return checkIfEventExists(name, desc, appID);
    }

    private String checkIfEventExists(String name, String desc, int appID) throws IOException {
        ByteArrayOutputStream out;
        String query = "SELECT event_id FROM event ";
        query += "WHERE name='" + name + "' AND description = '" + desc + "' AND application_id ='" + appID + "' LIMIT 1;";
        Log.i("QUERY", query);
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
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
            Log.i(TAG, out.toString());
        } finally {
            connection.disconnect();
        }
        return out.toString();
    }

    private String findAppId(String s) throws IOException {
        ByteArrayOutputStream out;
        String query = "SELECT application_id FROM application ";
        query += "WHERE name='" + s + "';";
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();
            out = new ByteArrayOutputStream();
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
            return out.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

