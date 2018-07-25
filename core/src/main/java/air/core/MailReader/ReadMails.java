package air.core.MailReader;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Priority;
import air.database.Bean.Users;
import air.database.ServicesImpl;
import air.webservices.EmailListener;

/**
 * Created by mruzman on 6.1.2018..
 */

public class ReadMails {
    private Users user;
    private Email mail;
    private Priority priority;
    private App app;

    private static String TAG = "READ MAILS";

    public ReadMails(Users user) {
        this.user = user;
        mail = null;
        app = null;
    }

    public void initializeEmail() {

    }

    public List<Object> checkIfNewEmailCame() throws ExecutionException, InterruptedException, IOException, JSONException {
        List<Object> objects = new ArrayList<Object>();
        List<Object> returnObjects = new ArrayList<Object>();
        objects = new ArrayList(new EmailListener().execute(user.getEmail(), user.getGmailPassword()).get());

        Log.i("LISTA", String.valueOf(objects.size()));
        if (objects != null || !objects.isEmpty()) {//ako je nesto doslo s listom
            for (Object object: objects) {
                if(object instanceof Email){
                    this.mail = (Email) object;
                    Log.i(">>>>>>>>>>>>>>>>LOG EMAILLLLLLL" , String.valueOf(mail.getEmailId()));
                }
                if(object instanceof App){
                    this.app = (App) object;
                    Log.i(">>>>>>>>>>>>LOG APPPP", String.valueOf(app.getApplicationId()));
                }
                if(mail != null  && app != null){
                    List<String> idData = new ServicesImpl().insertNewRecivedBug(mail, app, user.getUserId());
                    if(idData != null || !idData.isEmpty()){
                        app.setApplicationId(Integer.valueOf(idData.get(0)));
                        mail.setEmailId(Integer.valueOf(idData.get(2)));
                        returnObjects.add(app);
                        returnObjects.add(mail);
                    }
                    app = null;
                    mail = null;
                }
            }
        }
        return returnObjects;
    }

    private java.sql.Timestamp formatDate(String s) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(s);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        } catch (Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        return null;
    }
}
