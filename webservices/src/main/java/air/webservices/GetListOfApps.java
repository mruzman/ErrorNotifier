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
 * Created by Mateo on 19.1.2018..
 */

public class GetListOfApps extends AsyncTask<String, String, List<App>> {


    @Override
    protected List<App> doInBackground(String... strings) {
        List<App> appsList = new ArrayList<>();
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        try {
            String res2 =  new String(new ServicesImpl().getApps(strings[0]));
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
                    appsList.add(new App(o.getInt("application_id"), o.getString("name"), o.getInt("user_id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return appsList;

    }
}
