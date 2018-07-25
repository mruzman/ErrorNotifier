package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by mruzman on 15.11.2017..
 */

public class InsertNewUser extends AsyncTask<String, String, Integer> {
    private static String TAG = "InsertNewUser";

    @Override
    public Integer doInBackground(String... strings) {
        try {
            if (new ServicesImpl().insertNewUser(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]).toString() == "1")
                return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
