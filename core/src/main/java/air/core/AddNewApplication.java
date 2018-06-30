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

    public int insertApp()throws ExecutionException, InterruptedException{
        int checkIfAppExists = checkIfAppExists();
        if (checkIfAppExists == 0) {
            return 2;
        } else if (checkIfAppExists == -1) {
            return -1;
        } else {
            if (new InsertNewApp().execute(app.getName()).get() == 1) {
                return 1;
            } else {
                return -1;
            }
        }
    }

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
        if (value != null) {
            return 0;
        }
        return 1;
    }

    private App getApp(){
        return app;
    }
}
