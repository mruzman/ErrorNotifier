package air.core.MailReader;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.Bean.Email;
import air.core.Bean.Event;
import air.core.Bean.Priority;
import air.core.Bean.Users;
import air.database.ServicesImpl;
import air.webservices.EmailListener;

/**
 * Created by mruzman on 6.1.2018..
 */

public class ReadMails {
    private Users user;
    private Email mail;
    private Event event;
    private Priority priority;

    public ReadMails(Users user) {
        this.user = user;
    }

    public void initializeEmail() {

    }

    public List<Object> checkIfNewEmailCame() throws ExecutionException, InterruptedException, IOException, JSONException {
        List<List<String>> lists;
        List<Object> objects = new ArrayList<Object>();
        lists = new ArrayList(new EmailListener().execute(user.getEmail(), user.getPassword()).get());
        Log.i("LISTA", String.valueOf(lists.size()));
        if (!lists.isEmpty()) {//ako je nesto doslo s listom
            for (List<String> list : lists) {
                //zapisati v Beanove i vrnuti ih u MainActivity -- 0 - appID, 1 - eventID, 2 - mailID
                List<String> newValues = new ServicesImpl().insertNewRecivedBug(list, user.getUserId());
                if (newValues.size() > 0) {
                    mail = new Email(1, list.get(2), //(int) Integer.valueOf(newValues.get(2)) --to ide mesto 1
                            list.get(3) == "" ? null : formatDate(list.get(3)),
                            list.get(4) == "" ? null : formatDate(list.get(4)), "UNSOLVED",
                            user.getUserId(), (int) Integer.valueOf(newValues.get(1)));
                    event = new Event((int) Integer.valueOf(newValues.get(1)), list.get(0), list.get(2), (int) Integer.valueOf(newValues.get(0)));
                    objects.add(mail);
                    objects.add(event);
                    Log.i("USERID:", String.valueOf(event.getName()));
                    Log.i("MAIL", String.valueOf(mail.getEmailId()));
                }
            }
            return objects;
        } else {
            return null;
        }
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
