package air.webservices;

import android.os.AsyncTask;

import java.io.IOException;

import air.database.ServicesImpl;

public class SetEmailAsSolved  extends AsyncTask<Integer, Void, Integer> {
    public Integer doInBackground(Integer... integers) {
        try {
            if (new ServicesImpl().setEmailAsSolved(integers[0]) == 1)
                return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
