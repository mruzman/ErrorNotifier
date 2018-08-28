package air.errornotifier;

import air.database.Bean.Email;

public class ResponseClass {
    /**
     * Otvori novi response ovisno o modulu
     *
     * @param activity
     * @param email
     * @param userId
     * @param module (activity - otvori novi activity, popup - otvori popup)
     * @return
     */
    protected Response getResponse(MainActivity activity, Email email, int userId, String module){
        Response response = new ResponseModule().getResponse(activity, email, userId, module);
        return response;
    }
}
