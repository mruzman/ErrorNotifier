package air.database.Bean;

import java.io.Serializable;

/**
 * Created by mruzman on 20.1.2018..
 */

public class Response implements Serializable {
    private int responseId;
    private int emailId;
    private int userId;
    private String response;

    public Response(int responseId, int emailId, int userId, String response) {
        this.responseId = responseId;
        this.emailId = emailId;
        this.userId = userId;
        this.response = response;
    }

    public Response() {
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
