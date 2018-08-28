package air.core;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.webservices.AddApplication;
import air.webservices.InsertNewApp;

/**
 * Created by Mateo on 6.12.2017..
 */

public class AddNewApplication {

    private static String TAG = "AddNewApplication";

    private App app;

    public AddNewApplication(App app) {
        this.app = app;
    }

    /**
     * Pozivanje webservisa za unos nove aplikacije
     *
     * @return 2 ako nije u redu, -1 ako je greška i 1 ako je unesena nova aplikacija
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public int insertApp()throws ExecutionException, InterruptedException{
        int checkIfAppExists = checkIfAppExists();
        if (checkIfAppExists == 0) {
            return 2;
        } else if (checkIfAppExists == -1) {
            return -1;
        } else {
            if (new InsertNewApp().execute(app.getName(), String.valueOf(app.getUserID())).get() == 1) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * Provjera da li aplikacija s tim nazivom već postoji
     *
     * @return 1 ako ne postoji, 0 ako postoji
     */
    private Integer checkIfAppExists() {
        String value = "";
        AddApplication addApp = new AddApplication();
        try {
            value = addApp.execute(getApp().getName()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
        if (value == "ok") {
            return 1;
        }
        return 0;
    }

    private App getApp(){
        return app;
    }
}
