package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import air.database.LogInCheck;

/**
 * Created by mruzman on 7.11.2017..
 */

public class LogIn extends AsyncTask<String, String, String> {
    private static final String TAG = "LogIn";

    @Override
    protected String doInBackground(String...strings) {
        try{
            String username = strings[0];
            String password = strings[1];
            String result = new LogInCheck().getUrlString(username,password);
            Log.i(TAG, "Dohvaceni podaci: "+ result);
        }catch (IOException e){
            Log.e(TAG, "Propao pokusaj dohvacanja: ", e);
        }
        return null;
    }
}
