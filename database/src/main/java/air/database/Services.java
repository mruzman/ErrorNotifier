package air.database;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by mruzman on 15.11.2017..
 */

interface Services {
    byte[] getLogin(String username, String password) throws IOException, JSONException;

    byte[] getIfExists(String username, String email) throws IOException;
}
