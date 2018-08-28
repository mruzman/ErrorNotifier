package air.errornotifier;

import air.database.Bean.Email;

public class ResponseClass {
    protected Response getResponse(MainActivity activity, Email email, int userId, String module){
        Response response = new ResponseModule().getResponse(activity, email, userId, module);
        return response;
    }
}
