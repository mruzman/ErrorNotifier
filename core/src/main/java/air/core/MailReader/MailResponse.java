package air.core.MailReader;

import air.database.Bean.Email;
import air.database.Bean.Event;
import air.database.Bean.Response;
import air.database.Bean.Users;

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

    public void insertAnswer(String answer){

    }
}
