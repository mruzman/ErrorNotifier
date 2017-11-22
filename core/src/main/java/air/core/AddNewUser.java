package air.core;

import android.util.Log;

import java.util.concurrent.ExecutionException;

import air.core.Bean.Users;
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
        int checkIfUserExists = checkIfUserExists();
        if (checkIfUserExists == 0) {
            Log.i(TAG, "KORISNIK POSTOJI!");
            return 2;
        } else if (checkIfUserExists == -1) {
            return -1;
        } else {
            Log.i(TAG, "KORISNIK NE POSTOJI!");
            if (new InsertNewUser().execute(user.getFirstName(), user.getLastName(), user.getUsername(),
                    user.getEmail(), user.getPassword()).get() == 1) {
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
            Log.i(TAG, value.toString());
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
