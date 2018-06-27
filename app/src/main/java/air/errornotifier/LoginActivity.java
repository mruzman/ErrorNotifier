package air.errornotifier;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import air.core.LogInCall;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "MAIN ACTIVITY";
    EditText UsernameEt, PasswrodEt;
    String etString;
    public static int admin =1;
    public static int user = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText) findViewById(R.id.txtUsername);
        PasswrodEt = (EditText) findViewById(R.id.txtPassword);
        UsernameEt.requestFocus();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

        if(isNetworkAvailable()== false){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            return;
        }
        LogInCall logInCall = new LogInCall(UsernameEt.getText().toString(), PasswrodEt.getText().toString());
        if (logInCall.getUser().getFirstName() !=null) {


            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            mainActivity.putExtra("User",(Serializable) logInCall.getUser());
            startActivity(mainActivity);

            //provjeri dal je admin Constant.TYPE = 1 (to bude za admin)
            //prebaci se na aktivnost logiran admin
            //else provjeri dal je korisnik
            //prebaci se na mod korisnik
        } else {
            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
            TextKeyListener.clear(UsernameEt.getText());
            TextKeyListener.clear(PasswrodEt.getText());
            UsernameEt.requestFocus();
        }
    }
}
