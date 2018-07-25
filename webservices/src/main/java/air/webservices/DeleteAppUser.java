package air.webservices;

import android.os.AsyncTask;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by Harm on 6.12.2017..
 */

public class DeleteAppUser extends AsyncTask<String, String, Integer> {

    private static String TAG = "DeleteAppUser";

    @Override
    public Integer doInBackground(String... strings) {
        try {
            if (new ServicesImpl().deleteAppUser(strings[0], strings[1]).toString() == "1")
                return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
