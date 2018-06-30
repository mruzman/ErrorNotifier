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
 * Created by mruzman on 14.11.2017..
 */

public class AddUser extends AsyncTask<String, String, String> {
    private static String TAG = "AddUser";

    @Override
    protected String doInBackground(String... strings) {
        JSONObject jsonObject = null;
        List<Users> usersList = new ArrayList<>();
        try {
            String username = strings[0];
            String email = strings[1];
            ServicesImpl services = new ServicesImpl();
            String result = new String(services.getIfExistsUser(username, email));
            jsonObject= new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            if(jsonArray != null){
                for(int i = 0;i<jsonArray.length(); i++){
                    JSONObject o = null;
                    try {
                        o = jsonArray.getJSONObject(i);
                        usersList.add(new Users(o.getInt("user_id"), o.getString("first_name"), o.getString("last_name"), o.getString("username"), o.getString("email"), o.getString("type"), o.getString("password"), o.getString("emailPass")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (usersList.size() != 0) {
                return "exists";
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
