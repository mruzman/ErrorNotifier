package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by mruzman on 14.11.2017..
 */

public class AddUser extends AsyncTask<String, String, String> {
    private static String TAG = "AddUser";

    @Override
    protected String doInBackground(String... strings) {
        try {
            String username = strings[0];
            String email = strings[1];
            ServicesImpl services = new ServicesImpl();
            String result = new String(services.getIfExistsUser(username, email));
            if (!result.isEmpty()) {
                Log.i(TAG, "Dohvaceni podaci: " + result);
                return "exists";
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
        }
        return null;
    }
}
