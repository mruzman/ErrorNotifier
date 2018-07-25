package air.webservices;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import air.database.Bean.Response;
import air.database.ServicesImpl;

/**
 * Created by mruzman on 21.1.2018..
 */

public class AllRespondedMails extends AsyncTask<String,Void,List<Response>>{
    @Override
    protected List<Response> doInBackground(String... strings) {
        List<Response> responses = new ArrayList<Response>();
        try {
            String emailID = strings[0];
            JSONObject o = new JSONObject(new ServicesImpl().getAllResponsesForProblem(emailID).toString());
            JSONArray jsonArray = o.getJSONArray("responses");
            for(int i=0;i<jsonArray.length();i++){
                o = jsonArray.getJSONObject(i);
                responses.add(new Response(o.getInt("response_id"),o.getInt("email_id"),o.getInt("user_id"),o.getString("response"),new Date(o.getString("date_respond"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return responses;
        } catch (IOException e) {
            e.printStackTrace();
            return responses;
        }
        return responses;
    }
}
