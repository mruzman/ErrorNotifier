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

/**
 * Provjeravanje baze o zapisanim novim email-ovima za prijavljenog korisnika
 *
 */
public class CheckDatabase extends Thread {

    private MainActivity activity;
    private TextView emailApp;
    private TextView emailDescription;
    private Thread thread;
    private int userId;
    public static volatile boolean isOpen =  true;

    public CheckDatabase(MainActivity activity) {
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
            /**
             * Provjera da li korisnik treba dobivati obavijesti od neke aplikacije
             */
            if (listUserApps.size() > 0) {
                List<Email> mailovi = null;
                try {
                    mailovi = StaticMethodes.getAllUnhandeledEmails(listUserApps);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                int prioritet = 3;
                /**
                 * Provjera da li ima neki novi email
                 *
                 */
                if(mailovi.size() > 0){
                    for(final Email email : mailovi){
                        for (Priority priority : listUserApps){
                            if(priority.getApplicationId() == email.getAppId()){
                                prioritet = priority.getPriority();
                                break;
                            }
                        }
                        try {
                            /**
                             * Odgodi prikaz obavijesti za 10 sekundi
                             */
                            Thread.sleep(10*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        /**
                         * Provjeri da li je već popup ili aktivnost otvorena, ako nije, prikaži
                         *
                         */
                        if(isOpen) {
                            isOpen = false;
                            /**
                             * Ovisno o prioritetu koju korisnik ima, prikaži mogućnost odgovora
                             * 1 - prikaži mogućnost odgovora odmah
                             * 2 - prikaži nakon 20 sekundi
                             * 3 - prikaži nakon 40 sekundi
                             *
                             */
                            switch (prioritet) {

                                case 1:
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Response response = new ResponseClass().getResponse(activity, email, userId, "popup");
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

            }
            try {
                /**
                 * Provjeravaj bazu svaku minutu o dospijeću novih mail-ova
                 *
                 */
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prikaz mogućnosti odgovora za prioritet 2
     *
     * @param email
     */
    private void priority2check(final Email email){

        thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    /**
                     * Provjeri da li je već netko preuzeo zadatak
                     *
                     */
                    if(StaticMethodes.isStilUnsolved(email)){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Response response = new ResponseClass().getResponse(activity, email, userId, "popup");
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

    /**
     * Prikaz mogućnosti odgovora za prioritet 3
     *
     * @param email
     */
    private void lastPriorityCheck(final Email email){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20*1000);

                    /**
                     * Provjeri da li je već netko preuzeo zadatak
                     *
                     */
                    if(StaticMethodes.isStilUnsolved(email)) {

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Response response = new ResponseClass().getResponse(activity, email, userId, "activity");
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