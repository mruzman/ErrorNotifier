package air.webservices;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import air.database.ServicesImpl;

public class GetUserApps extends AsyncTask<Integer, String, List<Integer>> {
    @Override
    protected List<Integer> doInBackground(Integer... integers) {
        List<Integer> apps = new ArrayList<>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        ServicesImpl services = new ServicesImpl();
        try {
            String res2 =  new String(new ServicesImpl().getUserApps(integers[0]));
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
                    apps.add(o.getInt("application_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return apps;
    }
}
