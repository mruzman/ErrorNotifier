package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

/**
 * Created by mruzman on 5.12.2017..
 */

public class EmailListener extends AsyncTask<String, Void, Void> {
    private static String T = "MEJLOVI";

    @Override
    protected Void doInBackground(String... strings) {

        String username = strings[0];
        String passwrod = strings[1];

        Properties properties = System.getProperties();
        if (properties == null) {
            Log.i(T, "Properties su null");
        } else {
            properties.setProperty("mail.store.protocol", "imaps");
        }
        try {
            Session session = Session.getDefaultInstance(properties, null);

            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, passwrod);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);

            Log.i(T, "Broj neprocitanih mailova:" + inbox.getUnreadMessageCount());

            GregorianCalendar lastThreeDays = new GregorianCalendar();
            lastThreeDays.add(Calendar.DATE, -3);
            Log.i("Prosli tjedan", lastThreeDays.getTime().toString());


            Message[] messages = inbox.getMessages();
            int i = messages.length - 1;
            while (true) {
                Message individualMsg = messages[i];
                Log.i("last three days", String.valueOf(lastThreeDays.getTimeInMillis()));
                Log.i("recive", String.valueOf(individualMsg.getReceivedDate().getTime()));
                if (lastThreeDays.getTimeInMillis() < individualMsg.getReceivedDate().getTime()) {
                    if (individualMsg.getSubject().contains("[Zabbix]") && !individualMsg.isSet(Flags.Flag.SEEN)) {
                        Log.i("Primljeno", individualMsg.getReceivedDate().toString());
                        Log.i("Email subject", individualMsg.getSubject());
                        individualMsg.setFlag(Flags.Flag.SEEN, true);

                    }
                    i--;
                } else {
                    Log.i("BREAK", "Brejkalo je");
                    break;
                }
            }

            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
