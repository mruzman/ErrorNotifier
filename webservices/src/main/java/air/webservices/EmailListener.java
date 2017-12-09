package air.webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;
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
            store.connect("imap.gmail.com", username,passwrod);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new ReceivedDateTerm(ComparisonTerm.GT, new Date()));

            FetchProfile fp =  new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            inbox.fetch(messages, fp);
            Log.i(T, "Broj neprocitanih mailova:" + inbox.getUnreadMessageCount());

            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
