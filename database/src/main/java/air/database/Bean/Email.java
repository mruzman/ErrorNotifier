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
    private String status;
    private int application;
    private int closed;
    private java.sql.Timestamp timeEventClosed;
    private int userId;
    private String userFirstname;
    private String userLastname;

    public Email(int emailId, String header, String description, java.sql.Timestamp timeEventOccured, String status, int application, int userId) {
        this.emailId = emailId;
        this.header = header;
        this.description = description;
        this.timeEventOccured = timeEventOccured;
        this.status = status;
        this.application = application;
        this.userId = userId;
    }

    public Email(int emailId, String header, String description, java.sql.Timestamp timeEventOccured, String status, int application, int closed, java.sql.Timestamp timeEventClosed, int userId) {
        this.emailId = emailId;
        this.header = header;
        this.description = description;
        this.timeEventOccured = timeEventOccured;
        this.status = status;
        this.application = application;
        this.closed = closed;
        this.timeEventClosed = timeEventClosed;
        this.userId = userId;
    }

    public Email(int emailId, String header, String description, java.sql.Timestamp timeEventOccured, String status, int application, String userFirstname, String userLastname, int userId) {
        this.emailId = emailId;
        this.header = header;
        this.description = description;
        this.timeEventOccured = timeEventOccured;
        this.status = status;
        this.application = application;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.userId = userId;
    }

    public Email(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAppId() {
        return application;
    }

    public void setAppId(int appId) {
        this.application = appId;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimeEventClosed(java.sql.Timestamp timeEventClosed) {
        this.timeEventClosed = timeEventClosed;
    }

    public void setUserFirstname(String userFirstname){
        this.userFirstname = userFirstname;
    }

    public String getUserFirstname(){
        return this.userFirstname;
    }

    public void setUserLastname(String userLastname){
        this.userLastname = userLastname;
    }

    public String getUserLastname(){
        return this.userLastname;
    }
}
