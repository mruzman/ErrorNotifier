package air.errornotifier;

import android.os.Looper;
import android.os.TestLooperManager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.StaticMethodes;
import air.database.Bean.AppUser;
import air.database.Bean.Email;
import air.database.Bean.Priority;

public class ProvjeravajBazu extends Thread {

    private MainActivity activity;
    private TextView emailApp;
    private TextView emailDescription;
    private Thread thread;
    private int userId;

    public ProvjeravajBazu(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        List<Priority> listUserApps = new ArrayList<>();
        while (MainActivity.user.getEmail() != null) {
             userId = MainActivity.user.getUserId();
            try {
                listUserApps = StaticMethodes.getUserApps(userId);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (listUserApps.size() > 0) {
                List<Email> mailovi = null;
                try {
                    mailovi = StaticMethodes.getAllUnhandeledEmails(listUserApps);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //TODO PROVJERI PO PRIORITETIMA
                int prioritet = 3;
                if(mailovi.size() > 0){
                    for(final Email email : mailovi){
                        for (Priority priority : listUserApps){
                            if(priority.getApplicationId() == email.getAppId()){
                                prioritet = priority.getPriority();
                                break;
                            }
                        }
                        try {
                            Thread.sleep(10*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        switch (prioritet){

                            case 1:
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Response response = new PopupResponse(activity, "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId(), userId);
                                        response.CreatePopup(activity.getCurrentFocus());
                                    }
                                });
                                break;
                            case 2:
                                priority2check(email);
                                break;
                            default:
                                lastPriorityCheck(email);

                        }
                    }
                }

            }
            try {
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void priority2check(final Email email){

        thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(20*1000);
                    if(StaticMethodes.isStilUnsolved(email)){
                        Log.i("MAILOVI", "MAIL NIJE PREUZET!!!!");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Response response = new PopupResponse(activity, "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId(), userId);
                                response.CreatePopup(activity.getCurrentFocus());
                            }
                        });
                    }

                        //DAJ PUSH NOTIFIKACIJU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    private void lastPriorityCheck(final Email email){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(5*1000);
                    if(StaticMethodes.isStilUnsolved(email)) {
                        Log.i("PRIORITET3 MAIL", "MAIL NIJE PREUZET!!!!");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Response response = new ResponseCall(activity, "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId(), userId);
                                //Response response = new PopupResponse(activity, "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId(), MainActivity.user.getUserId());
                                response.CreatePopup(activity.getCurrentFocus());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}