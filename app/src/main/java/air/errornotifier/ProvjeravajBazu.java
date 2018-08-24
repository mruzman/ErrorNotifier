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
        try {
            listUserApps = StaticMethodes.getUserApps(MainActivity.user.getUserId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (listUserApps.size() > 0) {
            while (MainActivity.user.getEmail() != null) {
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
                    for(Email email : mailovi){
                        for (Priority priority : listUserApps){
                            if(priority.getApplicationId() == email.getAppId()){
                                prioritet = priority.getPriority();
                                break;
                            }
                        }
                        switch (prioritet){
                            case 1:
                                activity.ShowPopup(activity.getCurrentFocus(),"New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId());
                                break;
                            case 2:
                                priority2check(email);
                                break;
                            default:
                                lastPriorityCheck(email);

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
    }

    private void priority2check(final Email email){

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000);
                    if(StaticMethodes.isStilUnsolved(email)){
                        Log.i("MAILOVI", "MAIL NIJE PREUZET!!!!");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.ShowPopup(activity.getCurrentFocus(), "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId());
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
                    Thread.sleep(60*1000);
                    if(StaticMethodes.isStilUnsolved(email)){
                        Log.i("PRIORITET3 MAIL", "MAIL NIJE PREUZET!!!!");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.ShowPopup(activity.getCurrentFocus(), "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId());
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