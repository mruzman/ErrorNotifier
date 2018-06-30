package air.database.Bean;

import android.util.Log;

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

public class Users implements Serializable{
    private static final String TAG = "USERS";
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String type;
    private String mailPass;

    public Users(int userId, String firstName, String lastName, String username, String email, String type, String password, String mailPass) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.type = type;
        this.password = password;
        this.mailPass = mailPass;
    }

    public Users() {
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

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailPass() {
        return mailPass;
    }

    public void setMailPass(String mailPass) {
        this.mailPass = mailPass;
    }

    public void insertUser(Users user){

    }

    public static List<Users> getAll() throws IOException, JSONException {
        List<Users> usersList = new ArrayList<Users>();
        String result = new String(new ServicesImpl().getUsers());

        JSONArray jsonArray = new JSONArray(new JSONObject(result));
        if(jsonArray != null){
            for(int i = 0;i<jsonArray.length(); i++){
                JSONObject o = (JSONObject) jsonArray.get(i);
                usersList.add(new Users(o.getInt("userId"), o.getString("firstName"), o.getString("lastName"), o.getString("username"), o.getString("email"), o.getString("type"), o.getString("password"), o.getString("emailPass")));
            }
        }

        return usersList;
    }
}
