package air.webservices;

import air.database.LogIn;

/**
 * Created by mruzman on 3.11.2017..
 */

public class GetJSON {
    private String username;
    private String password;

    public GetJSON(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public GetJSON() {
    }

    public void getJSONValues() {
        String gettedValue;
        gettedValue = String.valueOf(new LogIn().execute(username, password));
        System.out.println(gettedValue);
    }

}
