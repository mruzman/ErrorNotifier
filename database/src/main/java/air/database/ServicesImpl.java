package air.database;

import android.util.Log;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        String query = "SELECT * FROM application WHERE name='"+ appName + "'";
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
            }else{
                connection.disconnect();
                return Constants.DATA_DOESNT_EXISTS_IN_DATABASE;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Constants.ERROR;
        }
    }


    @Override
    public Integer insertNewApp(String app) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO application(name) ";
        query +="VALUES('" + app + "')";
        Log.i(TAG, query);
        String createURL = Constants.URL + "?q=" + query;
        Log.i(TAG, createURL);
        URL url = new URL(createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                        ": with " + createURL);
            }
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while((byteRead = in.read(buffer))>0){
                out.write(buffer, 0,byteRead);
            }
            out.close();
            Log.i(TAG, out.toString());
        }finally {
            connection.disconnect();
        }
        return Integer.valueOf(out.toString());
    }


}

