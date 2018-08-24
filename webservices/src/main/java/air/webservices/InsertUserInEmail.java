package air.webservices;

import android.os.AsyncTask;

import java.io.IOException;

import air.database.ServicesImpl;

public class InsertUserInEmail extends AsyncTask<Integer, Void, Integer>{

    @Override
    public Integer doInBackground(Integer... integers) {
        try {
            if (new ServicesImpl().insertUserInEmail(integers[0], integers[1]) == 1)
                return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
