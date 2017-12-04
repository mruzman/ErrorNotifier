package air.errornotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

import air.core.AddNewUser;
import air.core.Bean.Users;

public class AddUserActivity extends AppCompatActivity {

    private static String TAG = "ADD USER ACTIVITY";
    private Button mAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mAddUser = (Button) findViewById(R.id.btnDodajKorisnika);

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    kreiraj_novog();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }



    private void kreiraj_novog() throws ExecutionException, InterruptedException  {

        Users user = new Users(1, "Mark", "Ružman", "mruz","mruz@errornotifier.com", "USER", "marko123");
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
    }
}
