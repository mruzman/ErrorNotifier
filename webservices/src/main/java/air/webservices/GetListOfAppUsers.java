package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.AppUser;
import air.database.Bean.Users;
import air.database.ServicesImpl;

/**
 * Created by Harm on 19.1.2018..
 */

public class GetListOfAppUsers extends AsyncTask<String, String, List<AppUser>> {


    @Override
    protected List<AppUser> doInBackground(String... strings) {
        List<AppUser> usersList = new ArrayList<AppUser>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        ServicesImpl services = new ServicesImpl();
        try {
            String res2 =  new String(new ServicesImpl().getAppUsers(Integer.parseInt(strings[0])));
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
                    usersList.add(new AppUser(o.getInt("user_id"), o.getString("first_name"), o.getString("last_name"), o.getInt("application_id"), o.getInt("priority")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return usersList;

    }
}
