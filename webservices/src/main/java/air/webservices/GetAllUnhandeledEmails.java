package air.webservices;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Priority;
import air.database.Bean.Users;
import air.database.ServicesImpl;
import air.database.helper.Constants;

public class GetAllUnhandeledEmails extends AsyncTask<List<Priority>, Void, List<Email>> {

    @Override
    protected List<Email> doInBackground(List<Priority>... apps) {
        String uvjet = "IN(";
        String zarez = "";
        for(Priority app: apps[0]){
            uvjet+= zarez+app.getApplicationId();
            zarez = ",";
        }
        uvjet +=")";
        List<Email> emails = new ArrayList<>();
        byte[] result = new byte[0];
        JSONObject jsonObject= null;
        JSONArray jsonArray = null;
        try {
            String res2 =  new String(new ServicesImpl().getUnhandledEmails(uvjet));
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
                    Email eMail = new Email();
                    eMail.setEmailId(o.getInt(Constants.EMAILID));
                    eMail.setHeader(o.getString(Constants.HEADER));
                    eMail.setDescription(o.getString(Constants.DESCRIPTION));
                    Date date = (Date) (new SimpleDateFormat("yyyy-dd-MM hh:mm:ss").parse(o.getString(Constants.TIMEEVENTOCCURED)));
                    java.sql.Timestamp timee = new java.sql.Timestamp(date.getTime());
                    eMail.setTimeEventOccured(timee);
                    eMail.setStatus(o.getString(Constants.STATUS));
                    eMail.setAppId(o.getInt(Constants.APPLICATION));
                    emails.add(eMail);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return emails;

    }
}
