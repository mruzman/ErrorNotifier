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
 * Created by Mateo on 20.8.2018..
 */

public class AppUser implements Serializable{
    private static final String TAG = "USERS";
    private int userId;
    private String firstName;
    private String lastName;
    private int applicationId;
    private int priority;

    public AppUser(int userId, String firstName, String lastName, int applicationId, int priority) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.applicationId = applicationId;
        this.priority = priority;
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

    public int getPriority(){return priority;}

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

    public void setPriority(int priority){this.priority = priority;}
}
