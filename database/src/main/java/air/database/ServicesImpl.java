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
        String query = "select * from user ";
        query += "where username='" + username + "' ";
        query += "and password='" + password + "'";
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
    public byte[] getIfExists(String username, String email) throws IOException {
        String query = "select * from user ";
        query += "where username='" + username + "' ";
        query += "or email='" + email + "'";
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
}

