package air.core;

import android.util.Log;

import air.core.Bean.Users;

/**
 * Created by mruzman on 14.11.2017..
 */

public class AddNewUser {

    private static String TAG = "AddNewUser";

    private Users user;

    public AddNewUser(Users users) {
        this.user = users;
    }

    public String insertNewUser(){
        if(!checkIfUserExists()) return
    }

    private boolean checkIfUserExists(){
        LogInCall call = new LogInCall(user.getUsername(), user.getPassword());
        Log.i(TAG, call.getUser().getFirstName().toString());
        if(call.getUser().equals("") || call.getUser().toString() == "") return false;
        return true;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
