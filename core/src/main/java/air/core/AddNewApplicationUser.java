package air.core;

import android.util.Log;

import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.AppUser;
import air.webservices.AddApplication;
import air.webservices.AddApplicationUser;
import air.webservices.InsertNewApp;
import air.webservices.InsertNewAppUser;

/**
 * Created by Mateo on 6.12.2017..
 */

public class AddNewApplicationUser {

    private static String TAG = "AddNewApplicationUser";

    private AppUser appUser;

    public AddNewApplicationUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public int insertAppUser()throws ExecutionException, InterruptedException{
        int checkIfAppExists = checkIfAppExists();
        if (checkIfAppExists == 0) {
            return 2;
        } else if (checkIfAppExists == -1) {
            return -1;
        } else {
            if (new InsertNewAppUser().execute(String.valueOf(appUser.getApplicationId()), String.valueOf(appUser.getUserId())).get() == 1) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private Integer checkIfAppExists() {
        String value = "";
        AddApplicationUser addAppUser = new AddApplicationUser();
        try {
            value = addAppUser.execute(String.valueOf(getApp().getApplicationId()), String.valueOf(getApp().getUserId())).get();
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

    private AppUser getApp(){
        return appUser;
    }
}
