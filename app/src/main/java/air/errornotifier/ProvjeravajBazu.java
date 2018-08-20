package air.errornotifier;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.StaticMethodes;
import air.database.Bean.App;
import air.database.Bean.Email;

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
        List<Integer> listUserApps = new ArrayList<>();
        try {
            listUserApps = StaticMethodes.getUserApps(MainActivity.user.getUserId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (listUserApps.size() > 0) {
            while (MainActivity.user.getEmail() != null) {
                //TODO PROVJERITI JELI DOŠAO NOVI MAIL!
                List<Email> mailovi = checkNewMail(listUserApps);
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }


    private void checkPriorityForApp() throws ExecutionException, InterruptedException, IOException {
        int priority = 0;
    }

//TODO DOVRŠITI
    private List<Email> checkNewMail(List<Integer> aplikacije) {
        List<Email> mail = new ArrayList<>();

        return mail;
    }
}