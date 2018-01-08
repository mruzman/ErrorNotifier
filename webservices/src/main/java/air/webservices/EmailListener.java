package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import air.database.helper.Constants;

/**
 * Created by mruzman on 5.12.2017..
 */

public class EmailListener extends AsyncTask<String, Void, List<List<String>>> {
    private static String T = "MEJLOVI";

    private String desc;
    private String time_error;
    private String time_warning;
    private String name;
    private String app;
    @Override
    protected List<List<String>> doInBackground(String... strings) {
        name = "";
        app = "";
        desc="";
        time_error="";
        time_warning="";

        List<List<String>> list = new ArrayList<List<String>>();
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

            GregorianCalendar lastThreeDays = new GregorianCalendar();
            lastThreeDays.add(Calendar.DATE, -3);


            Message[] messages = inbox.getMessages();
            int i = messages.length - 1;
            while (true) {
                Message individualMsg = messages[i];
                if (lastThreeDays.getTimeInMillis() < individualMsg.getReceivedDate().getTime()) {
                    if (individualMsg.getSubject().contains("Zabbix_problem") && !individualMsg.isSet(Flags.Flag.SEEN)) {
                        Log.i("Primljeno", individualMsg.getReceivedDate().toString());
                        Log.i("Email subject", individualMsg.getSubject());
                        //individualMsg.setFlag(Flags.Flag.SEEN, true); //TODO: UKLUCITI OVO
                        getStrings(individualMsg);

                        List<String> node = new ArrayList<String>();
                        node.add(name);
                        node.add(app);
                        node.add(desc);
                        node.add(time_error);
                        node.add(time_warning);

                        list.add(node);
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
        return list;
    }

    private void getStrings(Message message) throws MessagingException {
        String[] triggerName = message.getSubject().split("on");
        app = triggerName[1].split(":")[1];
        name = triggerName[0].split(":")[1];
    }
}

