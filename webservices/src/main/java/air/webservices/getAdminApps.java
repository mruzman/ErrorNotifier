package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.App;
import air.database.ServicesImpl;

public class getAdminApps extends AsyncTask<Integer, Void, List<App>>{

    @Override
    protected List<App> doInBackground(Integer... integers) {
        List<App> appsList = new ArrayList<>();
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        try {
            String res2 =  new String(new ServicesImpl().getAdminApps(integers[0]));
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
