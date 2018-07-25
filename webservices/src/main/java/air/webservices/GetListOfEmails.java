package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.Email;
import air.database.Bean.Users;
import air.database.ServicesImpl;

/**
 * Created by Harm on 19.1.2018..
 */

public class GetListOfEmails extends AsyncTask<String, String, List<Email>> {


    @Override
    protected List<Email> doInBackground(String... strings) {
        List<Email> emailList = new ArrayList<Email>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        ServicesImpl services = new ServicesImpl();
        try {
            String res2 =  new String(new ServicesImpl().getEmails(strings[0]));
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
                    emailList.add(new Email(o.getInt("email_id"), o.getString("header"), o.getString("description"),
                            java.sql.Timestamp.valueOf(o.getString("time_event_occured")), o.getInt("status"),  o.getInt("event_id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return emailList;

    }
}
