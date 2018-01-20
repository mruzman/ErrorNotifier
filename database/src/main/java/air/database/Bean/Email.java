package air.database.Bean;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by mruzman on 21.11.2017..
 */

public class Email implements Serializable {
    private int emailId;
    private String description;
    private java.sql.Timestamp timeEventOccured;
    private String status;
    private int userId;
    private int eventId;

    public Email(int emailId, String description, java.sql.Timestamp timeEventOccured, String status, int userId, int eventId) {
        this.emailId = emailId;
        this.description = description;
        this.timeEventOccured = timeEventOccured;
        this.status = status;
        this.userId = userId;
        this.eventId = eventId;
    }

    public Email() {
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Timestamp getTimeEventOccured() {
        return timeEventOccured;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setTimeEventOccured(java.sql.Timestamp timeEventOccured) {
        this.timeEventOccured = timeEventOccured;
    }
}
