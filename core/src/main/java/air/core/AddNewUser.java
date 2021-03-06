package air.core;

import android.util.Log;

import java.util.concurrent.ExecutionException;

import air.database.Bean.Users;
import air.webservices.AddUser;
import air.webservices.InsertNewUser;

/**
 * Created by mruzman on 14.11.2017..
 */

public class AddNewUser {
    private static String TAG = "AddNewUser";

    private Users user;

    public AddNewUser(Users users) {
        this.user = users;
    }

    public int insertNewUser() throws ExecutionException, InterruptedException {
        if(user.getFirstName().length() == 0 || user.getEmail().length() == 0 || user.getLastName().length() == 0 || user.getPassword().length() == 0 || user.getUsername().length() == 0 || user.getGmailPassword().length() == 0){
            return -2;
        }
        int checkIfUserExists = checkIfUserExists();
        if (checkIfUserExists == 0) {
            return 2;
        } else if (checkIfUserExists == -1) {
            return -1;
        } else {
            if (new InsertNewUser().execute(user.getFirstName(), user.getLastName(), user.getUsername(),
                    user.getEmail(), user.getPassword(), user.getGmailPassword()).get() == 1) {
                return 1;
            } else {//doslo je do greske
                return -1;
            }
        }
    }

    private Integer checkIfUserExists() {
        String value = "";
        AddUser addUser = new AddUser();
        try {
            value = addUser.execute(getUser().getUsername(), getUser().getEmail()).get();
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


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
