package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.App;
import air.database.ServicesImpl;

public class getAdminApps extends AsyncTask<Integer, Void, List<App>>{

    @Override
    protected List<App> doInBackground(Integer... integers) {
        List<App> appList = new ArrayList<>();
        //TODO CANNOT CAST PROVJERA
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        try {
            String res2 =  new String(new ServicesImpl().getApps());
            jsonObject= new JSONObject(res2);
            jsonArray = jsonObject.getJSONArray("records");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            JsonParser parser = new JsonParser();
//            String response = String.valueOf(new ServicesImpl().getAdminApps(integers[0]));
//            JsonElement yourJson = parser.parse(response);
//            Log.i(">>>RECORDOVI", yourJson.toString());
//            Type listType = new TypeToken<List<App>>() {}.getType();
//
//            appList = new Gson().fromJson(yourJson, listType);
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }
        return appList;
    }
}
