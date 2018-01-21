package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

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
                        app = new App();
                        email = new Email();
                        event = new Event();
                        getStrings(individualMsg);
                        String bp = getTextFromMessage(individualMsg);
                        //Timestamp timestamp = Timestamp.valueOf(getDate(getTextFromMessage(individualMsg)));
                        email.setTimeEventOccured(Timestamp.valueOf(getDate(bp)));
                        email.setDescription(getDescription(bp));
                        event.setDescription(getDescription(bp));
                        //TU DODAMO KJ ZAPISUJEM
                        list.add(email);
                        list.add(event);
                        list.add(app);
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

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    private String getDate(String s){
        String d= s.substring(s.indexOf("Problem started at"), s.indexOf("Problem name"));
        String date = d.split("on")[1].replace("/", "-").trim() + " " +d.split("at")[1].substring(1,9).trim();
        return date;
    }

    private String getDescription(String s){
        String problemName = s.substring(s.indexOf("Problem name"),s.indexOf("Host"));
        problemName = problemName.split(":")[1].trim();
        Log.i("PROBLEM NAME", problemName);
        return problemName;
    }
}

