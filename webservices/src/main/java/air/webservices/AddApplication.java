package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Users;
import air.database.ServicesImpl;

/**
 * Created by Harm on 19.1.2018..
 */

public class AddApplication extends AsyncTask<String, String, String> {
    private static String TAG = "AddApplication";

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0];
        List<App> appList = new ArrayList<App>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        ServicesImpl services = new ServicesImpl();
        try {
            String res2 =  new String(new ServicesImpl().getIfExistsApp(name));
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
                    appList.add(new App(o.getInt("application_id"), o.getString("name"), o.getInt("user_id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(appList.isEmpty()){
            return "ok";
        }

        return "exists";
    }
}