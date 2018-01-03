package air.errornotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import air.core.Bean.Users;
import air.core.LogInCall;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "MAIN ACTIVITY";
    EditText UsernameEt, PasswrodEt;
    String etString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText) findViewById(R.id.txtUsername);
        PasswrodEt = (EditText) findViewById(R.id.txtPassword);
        UsernameEt.requestFocus();
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


    public void prijava(View view){

        LogInCall logInCall = new LogInCall(UsernameEt.getText().toString(), PasswrodEt.getText().toString());
        if (logInCall.getUser().getFirstName() !=null) {


            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            mainActivity.putExtra("User",(Serializable) logInCall.getUser());
            startActivity(mainActivity);

          //  Toast.makeText(LoginActivity.this, "Uspješna prijava!!!", Toast.LENGTH_LONG).show();
            Log.i("MAIN", "Nije prazno");
            Log.i(TAG, "Prijavljen korisnik: " + logInCall.getUser().getFirstName() + " " + logInCall.getUser().getLastName());


            //provjeri dal je admin Constant.TYPE = 1 (to bude za admin)
            //prebaci se na aktivnost logiran admin
            //else provjeri dal je korisnik
            //prebaci se na mod korisnik
        } else {
            Toast.makeText(LoginActivity.this, "Pogrešan username ili lozinka", Toast.LENGTH_LONG).show();
            Log.i("MAIN", "NETOCNA PRIJAVA");
            UsernameEt.setText("");
            PasswrodEt.setText("");
            UsernameEt.requestFocus();
            //netocna prijava
        }
    }
}
