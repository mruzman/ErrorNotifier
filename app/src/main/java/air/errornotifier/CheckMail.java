package air.errornotifier;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.MailReader.MailResponse;
import air.core.MailReader.ReadMails;
import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Event;
import air.database.Bean.Response;
import air.database.Bean.Users;
import air.database.helper.Constants;
import air.webservices.PriorityApp;

/**
 * Created by mruzman on 29.3.2018..
 */

public class CheckMail extends Thread {


    private MainActivity activity;
    private Email mail;
    private Event event;
    private App app;
    private Response response;

    public CheckMail(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void run(){
        while (MainActivity.user != null) {
            try {
                mail = null;
                event = null;
                app = null;
                ReadMails readMails = new ReadMails(MainActivity.user);
                List<Object> insertedEmails = readMails.checkIfNewEmailCame();
                if (insertedEmails != null) {
                    for (Object object : insertedEmails) {
                        if (object instanceof Email) {
                            mail = (Email) object;
                        }
                        if (object instanceof Event) {
                            event = (Event) object;
                            Log.i("U MAINU--", String.valueOf(event.getApplicationId()));
                        }
                        if (object instanceof App) {
                            app = (App) object;
                        }
                        if (mail != null && event != null && app != null) {
                            checkPriorityForApp();
                            mail = null;
                            event = null;
                            app = null;
                        }
                    }

                } else {
                    Log.i("MAIN_ZA_LISTU", "Prazna je lista EMAILOVA");
                }
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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

    private void checkPriorityForApp() throws ExecutionException, InterruptedException {
        int priority = 0;
        if (event.getApplicationId() != 0) {
            priority = new PriorityApp().execute(MainActivity.user.getUserId(), event.getApplicationId()).get();
            Log.i("Prioritet", String.valueOf(priority));
            if (priority != 0) {
                if (priority == 1) {
                    response = new Response();
                    List<String> answer = activity.openDialogResponse(app.getName());
                    Log.i("ODGOVOR DOBIVENI", answer.get(1) + " " + answer.get(0));
                    if (answer.get(1) != "") {
                        response.setEmailId(mail.getEmailId());
                        response.setResponse(answer.get(1));
                        response.setUserId(MainActivity.user.getUserId());
                    }
                    mail.setStatus(answer.get(0));
                    MailResponse mailResponse = new MailResponse(mail, MainActivity.user, response);

                    if (response.getResponse() != "") {
                        mailResponse.insertAnswer(response.getResponse());
                    }
                    if (mail.getStatus() == Constants.STATUS_IN_PROGRESS || mail.getStatus() == Constants.STATUS_LATER) {
                        mailResponse.insertNewStatus();
                    }
                    //response.insertAnswer("TU IDE ODGOVOR");
                } else if (priority == 2) {


                } else {
                    //radi ono zadnje xD
                }
            }
        }
    }

}
