package air.errornotifier;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;

import java.util.concurrent.ExecutionException;

import air.core.AddNewUser;
import air.core.LogInCall;
import air.database.Bean.Users;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class LogInActivityIntegrationTest {



    @Test
    public void testLogIn() throws ExecutionException, InterruptedException {
        Users user = new Users(1, "Oli","Grbavac","oli4","ogrbavac@foi.hr","USER", "oli4");
        new AddNewUser(user).insertNewUser();

        LogInCall logInCall = new LogInCall(user.getUsername(), user.getPassword());
        assertNotNull("Neuspješni login sa ispravnim podacima",logInCall.getUser().getFirstName());

        logInCall = new LogInCall("Nepostojeći user", "Nepostojeći password");
        assertNull("Ispravni login sa neispravnim podacima",logInCall.getUser().getFirstName());

        logInCall = new LogInCall("", "Nepostojeći password");
        assertNull("Ispravni login sa neispravnim podacima",logInCall.getUser().getFirstName());

        logInCall = new LogInCall("Nepostojeći user", "");
        assertNull("Ispravni login sa neispravnim podacima",logInCall.getUser().getFirstName());

        logInCall = new LogInCall("", "");
        assertNull("Ispravni login sa neispravnim podacima",logInCall.getUser().getFirstName());

    }


}
