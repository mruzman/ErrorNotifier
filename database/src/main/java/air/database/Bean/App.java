package air.database.Bean;

import java.io.Serializable;

/**
 * Created by mruzman on 21.11.2017..
 */

public class App implements Serializable{
    private int appID;
    private String name;
    private int userID;

    public App(int applicationId, String name, int userID) {
        this.appID = applicationId;
        this.name = name;
        this.userID = userID;
    }

    public App() {
    }

    public int getApplicationId() {
        return appID;
    }

    public void setApplicationId(int applicationId) {
        this.appID = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
