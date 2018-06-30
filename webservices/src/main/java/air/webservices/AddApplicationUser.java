package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by Harm on 19.1.2018..
 */

public class AddApplicationUser extends AsyncTask<String, String, String> {
    private static String TAG = "AddApplicationUser";

    @Override
    protected String doInBackground(String... strings) {
        try {
            String appID = strings[0];
            String userID = strings[1];
            ServicesImpl services = new ServicesImpl();
            Integer result = new Integer(services.getIfExistsAppUser(appID, userID));
            if (result == 0) {
                return "exists";
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
        }
        return null;
    }
}