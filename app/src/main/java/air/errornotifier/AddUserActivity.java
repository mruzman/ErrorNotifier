package air.errornotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.concurrent.ExecutionException;

import air.core.AddNewUser;
import air.core.Bean.Users;

public class AddUserActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static String TAG = "ADD USER ACTIVITY";
    private Button mAddUser;
    private EditText mAddFirstName;
    private EditText mAddLastName;
    private EditText mAddUserName;
    private EditText mAddEmail;
    private EditText mAddPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddFirstName = (EditText) findViewById(R.id.txtAddFirstName);
        mAddLastName = (EditText) findViewById(R.id.txtAddLastName);
        mAddUserName = (EditText) findViewById(R.id.txtAddUserName);
        mAddEmail = (EditText) findViewById(R.id.inputEmail);
        mAddPassword = (EditText) findViewById(R.id.txtInputPass);


        mAddUser = (Button) findViewById(R.id.btnAddUser);

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    kreiraj_novog();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        });


    }


    private void kreiraj_novog() throws ExecutionException, InterruptedException  {

        Users user = new Users(1, mAddFirstName.getText().toString(),mAddLastName.getText().toString(), mAddUserName.getText().toString(),mAddEmail.getText().toString(), "USER", mAddPassword.getText().toString());
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
