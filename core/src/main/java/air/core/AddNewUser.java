package air.core;

import android.util.Log;

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
            Log.i(TAG, "Vraća ERROR!");
            return "Error!";
        } else {
            Log.i(TAG, "Vraća error");
            return "Error";
        }
    }

    private boolean checkIfUserExists() {
        //String value = AddUser.execute(getUser().getUsername(), getUser().getEmail()).get();
        /*if (value == "") {
            Log.i(TAG, "Vratio je true");
        }*/
        return true;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
