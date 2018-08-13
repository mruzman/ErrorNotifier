package air.errornotifier;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.MailReader.ReadMails;
import air.core.StaticMethodes;
import air.database.Bean.App;
import air.database.Bean.Email;
import air.webservices.PriorityApp;

/**
 * Created by mruzman on 29.3.2018..
 */

public class CheckMail extends Thread {

    private List<App> appList;
    private MainActivity activity;
    private Email mail;
    private App app;

    public CheckMail(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void run()  {
        //TODO treba provjeriti kojoj aplikaciji je ovaj user administrator nakon toga će provjeravati mail za točno tu aplikaciju
        try {
            appList = StaticMethodes.getAdminApps(MainActivity.user.getUserId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(appList.size() == 0){
            return;
        }
        while (MainActivity.user != null) {
            try {
                mail = null;
                app = null;
                ReadMails readMails = new ReadMails(MainActivity.user);
                List<Object> insertedEmails = readMails.checkIfNewEmailCame();
                if (insertedEmails != null) {
                    for (Object object : insertedEmails) {
                        if (object instanceof Email) {
                            mail = (Email) object;
                        }
                        if (object instanceof App) {
                            app = (App) object;
                        }
                        if (mail != null && app != null) {
                            checkPriorityForApp();
                            mail = null;
                            app = null;
                        }
                    }

                } else {
                    Log.i("MAIN_ZA_LISTU", "Prazna je lista EMAILOVA");
                }
                Thread.sleep(60 * 1000);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt(){

    }

    @Override
    public void start(){
        super.start();
    }

    private void checkPriorityForApp() throws ExecutionException, InterruptedException, IOException {
        int priority = 0;

        if (mail.getAppId() != 0) {
            priority = new PriorityApp().execute(MainActivity.user.getUserId(), mail.getAppId()).get();
            Log.i("Prioritet", String.valueOf(priority));
            if (priority != 0) {

            }
        }
    }
}
