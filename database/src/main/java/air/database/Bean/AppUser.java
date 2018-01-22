package air.database.Bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import air.database.ServicesImpl;

/**
 * Created by mruzman on 8.11.2017..
 */

public class AppUser implements Serializable{
    private static final String TAG = "USERS";
    private int userId;
    private String firstName;
    private String lastName;
    private int applicationId;

    public AppUser(int userId, String firstName, String lastName, int applicationId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.applicationId = applicationId;
    }

    public AppUser() {
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getApplicationId(){return applicationId;}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setApplicationId(int applicationId){this.applicationId = applicationId;}


}
