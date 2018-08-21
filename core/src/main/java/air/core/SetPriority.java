package air.core;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.webservices.AddApplication;
import air.webservices.InsertNewApp;
import air.webservices.InsertPriority;

/**
 * Created by Mateo on 6.12.2017..
 */

public class SetPriority {

    private static String TAG = "SetPriority";

    private int priority;
    private int userID;
    private int appID;

    public SetPriority(int priority, int userID, int appID) {
        this.priority = priority;
        this.userID = userID;
        this.appID = appID;
    }

    public int set()throws ExecutionException, InterruptedException{
        if (new InsertPriority().execute(priority, userID, appID).get() == 1) {
            return 1;
        } else {
            return -1;
        }

    }

}
