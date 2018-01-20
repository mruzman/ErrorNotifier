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

public class GetListOfUserApps extends AsyncTask<String, String, List<App>> {

    private Users user;
    @Override
    protected List<App> doInBackground(String... strings) {

        List<App> appsList = new ArrayList<App>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        ServicesImpl services = new ServicesImpl();
        try {
            String res2 =  new String(new ServicesImpl().getUserApps(strings[0]));
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
                    appsList.add(new App(o.getInt("application_id"), o.getString("name")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return appsList;

    }
}
