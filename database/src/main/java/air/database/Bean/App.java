package air.database.Bean;

import java.io.Serializable;

/**
 * Created by mruzman on 21.11.2017..
 */

public class App implements Serializable{
    private int appID;
    private String name;
    private int status;

    public App(int applicationId, String name, int status) {
        this.appID = applicationId;
        this.name = name;
        this.status = status; //status je 0 - neaktivna ili 1 - aktivna
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
