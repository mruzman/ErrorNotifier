package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import air.database.LogInCheck;

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
            String result = new LogInCheck().getUrlString(username, password);

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
