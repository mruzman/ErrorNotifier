package air.database.Bean;

import java.io.Serializable;

/**
 * Created by mruzman on 22.11.2017..
 */

public class Event implements Serializable{
    private int eventId;
    private String name;
    private String description;
    private int applicationId;

    public Event(int eventId, String name, String description, int applicationId) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.applicationId = applicationId;
    }

    public Event() {
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
