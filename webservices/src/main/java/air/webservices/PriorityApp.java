package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by mruzman on 8.1.2018..
 */

public class PriorityApp extends AsyncTask<Integer, Void, Integer>{

    @Override
    protected Integer doInBackground(Integer... integers) {
        int userID = integers[0];
        int appID = integers[1];
        int priority =0;
        JSONObject object= null;
        try {
            object = new JSONObject(new ServicesImpl().getUserPriority(userID,appID).toString());
            JSONArray jsonArray = object.getJSONArray("records");
            object = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
            if(object.length()!=0) priority = object.getInt("priority");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return priority;
    }
}
