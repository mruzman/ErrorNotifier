package air.errornotifier;

import air.database.Bean.Email;

public class ResponseModule {

    protected Response getResponse(MainActivity activity, Email email, int userId, String module) {
        Response response;
        if(module.equals("activity")){
            response = new ResponseCall(activity, "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId(), userId);

        }else{
            response = new PopupResponse(activity, "New problem found at application: " + String.valueOf(email.getAppId()), email.getDescription(), email.getEmailId(), userId);
        }

        return response;
    }
}
