package air.core.MailReader;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.Email;
import air.database.Bean.Event;
import air.database.Bean.Response;
import air.database.Bean.Users;
import air.webservices.AllRespondedMails;
import air.webservices.InsertNewAnswer;

/**
 * Created by mruzman on 20.1.2018..
 */

public class MailResponse {
    private Email email;
    private Users user;
    private Response response;

    public MailResponse(Email email, Users user, Response response) {
        this.email = email;
        this.user = user;
        this.response = response;
    }

    public void insertAnswer(String answer) throws ExecutionException, InterruptedException {
        Log.i("TU SMO", "TU SMO");
        new InsertNewAnswer().execute(String.valueOf(email.getEmailId()), String.valueOf(user.getUserId()), answer).get();
    }

    public List<Response> readMailResponses() throws ExecutionException, InterruptedException {
        List<Response> allRespondedProblems = new ArrayList<Response>();
        allRespondedProblems = new AllRespondedMails().execute(String.valueOf(email.getEmailId())).get();
        return allRespondedProblems;
    }

}