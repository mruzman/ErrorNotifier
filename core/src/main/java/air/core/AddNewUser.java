package air.core;

import android.util.Log;

import java.util.concurrent.ExecutionException;

import air.core.Bean.Users;
import air.webservices.AddUser;

/**
 * Created by mruzman on 14.11.2017..
 */

public class AddNewUser {
    private static String TAG = "AddNewUser";

    private Users user;

    public AddNewUser(Users users) {
        this.user = users;
    }

    public String insertNewUser() {
        if (!checkIfUserExists()) {
            Log.i(TAG, "KORISNIK POSTOJI!");
            return "Error!";
        } else {
            Log.i(TAG, "KORISNIK NE POSTOJI!");
            return "Error";
        }
    }

    private boolean checkIfUserExists() {
        String value="";
        AddUser addUser = new AddUser();
        try {
            value = addUser.execute(getUser().getUsername(), getUser().getEmail()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(value!=null){
            Log.i(TAG, value.toString());
            return false;
        }
        return true;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
