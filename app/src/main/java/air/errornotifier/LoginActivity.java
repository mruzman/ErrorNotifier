package air.errornotifier;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import air.core.AddNewUser;
import air.core.Bean.Users;
import air.core.LogInCall;
import air.database.helper.Constants;
import air.webservices.LogIn;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MAIN ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*private void kreiraj_novog() throws ExecutionException, InterruptedException {
        Evo ovdi je napravljeno kak se zove dok ADMIN hoce dodati novoga korisnika...
        jos budem dodal funkciju koja provjerava dal je admin, ali svejedno vi morate napraviti
        to da ADMIN jedini vidi ovakve stvari :)
        Users user = new Users(1, "Marko", "Ružman", "mruzman","mruzman@errornotifier.com", "USER", "marko123");
        int unesi;
        unesi = new AddNewUser(user).insertNewUser();
        Log.i(TAG, String.valueOf(unesi));
        if(unesi == 1){
            Log.i(TAG, "DOBRO JE");
        }else if(unesi == 2){
            Log.i(TAG, "KORISNIK POSTOJI!");
        }else{
            Log.i(TAG, "Potkrala se greska");
        }
    }*/

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
