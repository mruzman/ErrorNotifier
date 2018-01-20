package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.Users;
import air.database.ServicesImpl;

/**
 * Created by mruzman on 7.11.2017..
 */

public class LogIn extends AsyncTask<String, String, JSONObject> {
    private static final String TAG = "LogIn";

    @Override
    protected JSONObject doInBackground(String... strings) {

        try {
            String username = strings[0];
            String password = strings[1];
            ServicesImpl services = new ServicesImpl();
            String result = new String(services.getLogin(username,password));

            if (!result.isEmpty()) {
                Log.i(TAG, "Dohvaceni podaci: " + result);
                return new JSONObject(result);
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
        } catch (JSONException joe) {
            Log.e(TAG, "Nemogu parsirat JSON: ", joe);
        }
        return null;
    }
}
