package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.Users;
import air.database.ServicesImpl;

/**
 * Created by Harm on 19.1.2018..
 */

public class GetListOfUsers extends AsyncTask<String, String, List<Users>> {


    @Override
    protected List<Users> doInBackground(String... strings) {
        List<Users> usersList = new ArrayList<Users>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        ServicesImpl services = new ServicesImpl();
        try {
            String res2 =  new String(new ServicesImpl().getUsers());
            jsonObject= new JSONObject(res2);
            jsonArray = jsonObject.getJSONArray("records");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonArray != null){
            for(int i = 0;i<jsonArray.length(); i++){
                JSONObject o = null;
                try {
                    o = jsonArray.getJSONObject(i);
                    usersList.add(new Users(o.getInt("user_id"), o.getString("first_name"), o.getString("last_name"), o.getString("username"), o.getString("email"), o.getString("type"), o.getString("password"), o.getString("gmail_password")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return usersList;

    }
}
