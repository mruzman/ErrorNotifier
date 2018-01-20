package air.webservices;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Date;

import air.database.Bean.Response;
import air.database.ServicesImpl;

/**
 * Created by mruzman on 20.1.2018..
 */

public class InsertNewAnswer extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Response response = new Response();
            response.setEmailId(Integer.valueOf(strings[0]));
            response.setUserId(Integer.valueOf(strings[1]));
            response.setResponse(strings[2]);
            new ServicesImpl().insertRespond(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}