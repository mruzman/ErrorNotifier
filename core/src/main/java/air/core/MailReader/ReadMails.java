package air.core.MailReader;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
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

    public ReadMails(Users user){
        this.user = user;
    }

    public void initializeEmail(){

    }

    public int checkIfNewEmailCame() throws ExecutionException, InterruptedException, IOException, JSONException {
        List<List<String>> lists;
        lists = new ArrayList(new EmailListener().execute(user.getEmail(),user.getPassword()).get());
        Log.i("LISTA", String.valueOf(lists.size()));
        if (!lists.isEmpty()){//ako je nesto doslo s listom
            for(List<String> list : lists){
                Log.i("U LISTI SE NALAZI", list.get(0) + " "+ list.get(1)+ list.get(2)+ list.get(3) + list.get(4));
                List<String> newValues = new ServicesImpl().insertNewRecivedBug(list, user.getUserId());
            }
            return 1;
        }else{
            return 0;
        }
    }
}
