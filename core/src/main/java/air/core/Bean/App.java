package air.core.Bean;

/**
 * Created by mruzman on 21.11.2017..
 */

public class App {
    private int applicationId;
    private String name;

    public App(int applicationId, String name) {
        this.applicationId = applicationId;
        this.name = name;
    }

    public App() {
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
