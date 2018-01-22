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
            String res2 =  new String(new ServicesImpl().getAppUsers());
            Log.i("RES2", res2.toString());
            jsonObject= new JSONObject(res2);
            jsonArray = jsonObject.getJSONArray("records");

        } catch (IOException e) {
            Log.i("ZBLJ", "ZBLJUB");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i("ZBLJ", "ZBLJUB2");
            e.printStackTrace();
        }
        if(jsonArray != null){
            for(int i = 0;i<jsonArray.length(); i++){
                JSONObject o = null;
                try {
                    o = jsonArray.getJSONObject(i);
                    usersList.add(new AppUser(o.getInt("user_id"), o.getString("first_name"), o.getString("last_name"), o.getInt("applicationID")));
                    Log.i("AppIDD: ", String.valueOf(o.getInt("applicationID")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return usersList;

    }
}
