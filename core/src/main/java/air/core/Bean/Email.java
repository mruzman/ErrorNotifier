package air.core.Bean;

import java.io.Serializable;
import java.security.Timestamp;
import java.sql.Time;

/**
 * Created by mruzman on 21.11.2017..
 */

public class Email implements Serializable{
    private int emailId;
    private String description;
    private Timestamp timeError;
    private Timestamp timeWarning;
    private String status;
    private int userId;
    private int eventId;

    public Email(int emailId, String description, Timestamp timeError, Timestamp timeWarning, String status, int userId, int eventId) {
        this.emailId = emailId;
        this.description = description;
        this.timeError = timeError;
        this.timeWarning = timeWarning;
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

    public Timestamp getTimeError() {
        return timeError;
    }

    public void setTimeError(Timestamp timeError) {
        this.timeError = timeError;
    }

    public Timestamp getTimeWarning() {
        return timeWarning;
    }

    public void setTimeWarning(Timestamp timeWarning) {
        this.timeWarning = timeWarning;
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
}
