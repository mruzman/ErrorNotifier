package air.core.IncomeMessages;

import android.os.AsyncTask;
import android.util.Log;

import air.core.Bean.Users;
import air.webservices.EmailListener;

/**
 * Created by mruzman on 5.12.2017..
 */

public class IncomingMail extends AsyncTask {

    private static String T = "MEJLOVI";
    private Users user;

    public IncomingMail(Users user) throws Exception {
        this.user = user;
        Log.i("MAIL", user.getEmail() + " " + user.getPassword() + " " + user.getEmail());
        this.execute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        while (true) {
            Log.i(T, String.valueOf(user.getEmail().toString().equals("")));
            if (!user.getEmail().toString().equals("")) {
                new EmailListener().execute(user.getEmail(), user.getPassword());
            } else {
                return null;
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
