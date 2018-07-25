package air.database.Bean;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by mruzman on 21.11.2017..
 */

public class Email implements Serializable {
    private int emailId;
    private String header;
    private String description;
    private java.sql.Timestamp timeEventOccured;
    private int status;
    private int appId;
    private int closed;
    private java.sql.Timestamp timeEventClosed;

    public Email(int emailId, String header, String description, java.sql.Timestamp timeEventOccured, int status, int appId) {
        this.emailId = emailId;
        this.description = description;
        this.timeEventOccured = timeEventOccured;
        this.status = status;
        this.appId = appId;
        this.header = header;
    }

    public Email(int emailId, String header, String description, java.sql.Timestamp timeEventOccured, int status, int appId, int closed, java.sql.Timestamp timeEventClosed) {
        this.emailId = emailId;
        this.header = header;
        this.description = description;
        this.timeEventOccured = timeEventOccured;
        this.status = status;
        this.appId = appId;
        this.closed = closed;
        this.timeEventClosed = timeEventClosed;
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

    public void setTimeEventOccured(java.sql.Timestamp timeEventOccured) {
        this.timeEventOccured = timeEventOccured;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public java.sql.Timestamp getTimeEventClosed() {
        return timeEventClosed;
    }

    public void setTimeEventClosed(java.sql.Timestamp timeEventClosed) {
        this.timeEventClosed = timeEventClosed;
    }
}
