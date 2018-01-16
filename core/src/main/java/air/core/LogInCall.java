package air.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import air.database.Bean.Users;
import air.webservices.LogIn;

/**
 * Created by mruzman on 8.11.2017..
 */

public class LogInCall {

    private static Users user;

    public LogInCall(String username, String password){
        JSONObject object = null;
        try {
            object = new LogIn().execute(username, password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(object != null) {
            for (int i = 0; i < object.length(); i++) {
                user = new Users();
                try {
                    user.setFirstName(object.getString("first_name"));
                    user.setLastName(object.getString("last_name"));
                    user.setType(object.getString("type"));
                    user.setEmail(object.getString("email"));
                    user.setUserId(object.getInt("user_id"));
                    user.setPassword(object.getString("password"));
                    user.setUsername(object.getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            user = new Users();
        }
    }

    public Users getUser(){
        return user;
    }
}
