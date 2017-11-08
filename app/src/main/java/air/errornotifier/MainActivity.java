package air.errornotifier;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import air.core.LogInCall;
import air.database.helper.Constants;
import air.webservices.LogIn;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void prijava(){
        //dok klikne na button prijava onda nek se pozove ovo samo umjesto jbond upisite ovo
        //sto je getano u textinputima...
        LogInCall logInCall= new LogInCall("jbond","jbond");
        if (logInCall.getUser().getFirstName() !=null) {
            Log.i("MAIN", "Nije prazno" );
            //provjeri dal je admin Constant.TYPE = 1 (to bude za admin)
            //prebaci se na aktivnost logiran admin
            //else provjeri dal je korisnik
            //prebaci se na mod korisnik
        } else {
            Log.i("MAIN", "NETOCNA PRIJAVA");
            //netocna prijava
        }
    }
}
