package air.errornotifier;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.StaticMethodes;
import air.database.Bean.AppUser;
import air.database.Bean.Email;
import air.database.Bean.Priority;

public class ProvjeravajBazu extends Thread {

    private MainActivity activity;

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
                    Thread.sleep(60*1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    private void lastPriorityCheck(Email email){

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60*1000);

                } catch (InterruptedException e) {
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