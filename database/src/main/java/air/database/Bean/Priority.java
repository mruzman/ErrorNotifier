package air.database.Bean;

import java.io.Serializable;

/**
 * Created by mruzman on 22.11.2017..
 */

public class Priority implements Serializable{
    private int userId;
    private int applicationId;
    private int priority;

    public Priority() {
    }

    public Priority(int userId, int applicationId, int priority) {
        this.userId = userId;
        this.applicationId = applicationId;
        this.priority = priority;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
