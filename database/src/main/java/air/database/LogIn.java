package air.database;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import air.database.helper.Constants;

/**
 * Created by mruzman on 3.11.2017..
 */

public class LogIn extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            String username = strings[0];
            String password = strings[1];
            String query = "select * from users ";
            query += "where username='" + username + "' ";
            query += "and password='" + password + "'";
            String createURL = Constants.URL + "?q=" + query;
            URL url = new URL(createURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }
            return sb.toString().trim();
        } catch (Exception e) {
            return null;
        }
    }
}
