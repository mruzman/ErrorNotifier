package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Event;
import air.database.helper.Constants;

/**
 * Created by mruzman on 5.12.2017..
 */

public class EmailListener extends AsyncTask<String, Void, List<Object>> {
    private static String T = "MEJLOVI";
    Email email;
    Event event;
    App app;

    @Override
    protected List<Object> doInBackground(String... strings) {

        List<Object> list = new ArrayList<Object>();
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
                    if (individualMsg.getSubject().contains(Constants.ZABBIX_STRING) && !individualMsg.isSet(Flags.Flag.SEEN)) {
                        Log.i("Primljeno", individualMsg.getReceivedDate().toString());
                        Log.i("Email subject", individualMsg.getSubject());
                        //individualMsg.setFlag(Flags.Flag.SEEN, true); //TODO: UKLUCITI OVO DOK BUDEM PREDAVAL
                        app = new App();
                        email = new Email();
                        event = new Event();
                        getStrings(individualMsg);

                        /*
                        TU DODATI OVO KJ FALI
                        DOLE JE ZA PARSANJE DATUMA
                         */

                        //TU DODAMO KJ ZAPISUJEM
                        if ( email.getTimeWarning() == null) {
                            email.setTimeWarning(formatDate(Constants.NULL_DATE));
                            Log.i("TIME", email.getTimeError().toString());
                        }
                        if ( email.getTimeError() == null) {
                            email.setTimeError(formatDate(Constants.NULL_DATE));
                        }
                        if (event.getDescription() == "" || event.getDescription() == null || email.getDescription() == null || email.getDescription() =="") {
                            event.setDescription("OPIS");
                            email.setDescription("OPIS");
                        }
                        list.add(email);
                        list.add(event);
                        list.add(app);
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
        app.setName(triggerName[1].split(":")[1]);
        if(triggerName[0].contains("Re")){
            event.setName(triggerName[0].split(":")[2]);
        }else {
            event.setName(triggerName[0].split(":")[1]);
        }
    }

    private java.sql.Timestamp formatDate(String s) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(s);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            Log.i("TIMESTAMP", timestamp.toString());
            return timestamp;
        } catch (Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        return null;
    }
    // TODO TU FALI JOS DOHVACANJE DATUMA I TIH STVARI Z BODY PARTA PORUKE..
}

