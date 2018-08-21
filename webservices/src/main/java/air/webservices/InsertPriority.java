package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.ServicesImpl;

/**
 * Created by Mateo on 21.8.2018..
 */

public class InsertPriority  extends AsyncTask<Integer, String, Integer> {

    private static String TAG = "InsertPriority";

    @Override
    public Integer doInBackground(Integer... integers) {
        try {
            if (new ServicesImpl().insertPriority(integers[0], integers[1], integers[2]) == 1)
                return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
