package air.errornotifier;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;

import air.core.LogInCall;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "MAIN ACTIVITY";
    public static String USER = "User";
    EditText UsernameEt, PasswrodEt;
    String etString;
    public static int admin =1;
    public static int user = 0;
    public static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText) findViewById(R.id.txtUsername);
        PasswrodEt = (EditText) findViewById(R.id.txtPassword);
        UsernameEt.requestFocus();
        Log.i(TAG, "Loginnnn" + String.valueOf(MainActivity.user));

        if(MainActivity.user != null){
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            mainActivity.putExtra(USER,(Serializable) MainActivity.user);
            startActivity(mainActivity);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Provjera da li je uključen internet
     *
     * @return
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Prijava korisnika
     *
     * @param view
     */
    public void prijava(View view){
        pd = ProgressDialog.show(this, "Logging you in", "Please wait...");

        if(isNetworkAvailable()== false){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            return;
        }

        /**
         * Novi loginCall
         *
         */
        LogInCall logInCall = new LogInCall(UsernameEt.getText().toString(), PasswrodEt.getText().toString());
        if (logInCall.getUser().getFirstName() !=null) {
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            mainActivity.putExtra(USER,(Serializable) logInCall.getUser());
            startActivity(mainActivity);
        } else {
            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
            TextKeyListener.clear(UsernameEt.getText());
            TextKeyListener.clear(PasswrodEt.getText());
            UsernameEt.requestFocus();
        }
    }
}
