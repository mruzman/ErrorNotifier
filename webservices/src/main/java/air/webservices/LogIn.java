package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.Users;
import air.database.ServicesImpl;

/**
 * Created by mruzman on 7.11.2017..
 */

public class LogIn extends AsyncTask<String, String, JSONObject> {
    private static final String TAG = "LogIn";

    @Override
    protected JSONObject doInBackground(String... strings) {
        List<Users> usersList = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            String username = strings[0];
            String password = strings[1];
            ServicesImpl services = new ServicesImpl();
            String result = new String(services.getLogin(username,password));
            jsonObject= new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            if(jsonArray != null){
                for(int i = 0;i<jsonArray.length(); i++){
                    JSONObject o = null;
                    try {
                        o = jsonArray.getJSONObject(i);
                        usersList.add(new Users(o.getInt("user_id"), o.getString("first_name"), o.getString("last_name"), o.getString("username"), o.getString("email"), o.getString("type"), o.getString("password")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (usersList.size() != 0) {
                return jsonArray.getJSONObject(0);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException joe) {
            joe.printStackTrace();
        }
        return null;
    }
}
