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
public class AddUserActivityIntegrationTest{


    @Test
    public void testLogIn() throws ExecutionException, InterruptedException {
        Users user = new Users(1, "Yakov", "Fain", "Yako", "Yako@gmail.com", "USER", "Yako","Yako");
        int unos = new AddNewUser(user).insertNewUser();
        assertEquals("Uneseni podaci nisu potpuni", 1, unos);

        user = new Users(1, "", "", "", "", "", "","");
        assertEquals("Nisu uneseni nikakvi podaci", 1, unos);


        user = new Users(1, "Zlatko", "", "", "zstapic", "", "","");
        assertEquals("Uneseni podaci nisu potpuni", 1, unos);

        user = new Users(1, "", "", "ogrbavac", "", "", "oli","oli");
        assertEquals("Uneseni podaci nisu potpuni", 1, unos);


    }
}