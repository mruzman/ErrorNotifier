package air.errornotifier;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.StaticMethodes;
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
                //TODO Ako su dobiveni mailovi odhandlaj ih
                List<Email> mailovi = null;
                try {
                    mailovi = StaticMethodes.getAllUnhandeledEmails(listUserApps);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(mailovi.size() > 0){
                    Log.i("MAILOVIIIIIII", String.valueOf(mailovi.size()));
                }
                try {
                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}