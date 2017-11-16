package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by mruzman on 15.11.2017..
 */

public class InsertNewUser extends AsyncTask<String, String, Void> {
    private static String TAG = "InsertNewUser";

    @Override
    protected Void doInBackground(String... strings) {
        ServicesImpl services = new ServicesImpl();
        try {
            services.insertNewUser(strings[0],strings[1],strings[2],strings[3],strings[4]);
            Log.i(TAG, "User unesen!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
