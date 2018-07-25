package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by Harm on 19.1.2018..
 */

public class AddApplication extends AsyncTask<String, String, String> {
    private static String TAG = "AddApplication";

    @Override
    protected String doInBackground(String... strings) {
        try {
            String name = strings[0];
            ServicesImpl services = new ServicesImpl();
            Integer result = new Integer(services.getIfExistsApp(name).toString());
            /*if (result == 0) {
                Log.i(TAG, "Dohvaceni podaci: " + result);
                return "exists";
            }*/

        } catch (IOException ioe) {
            Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
        }
        return null;
    }
}