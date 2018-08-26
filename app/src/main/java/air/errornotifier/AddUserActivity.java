package air.errornotifier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

import air.core.AddNewUser;
import air.database.Bean.Users;

public class AddUserActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static String TAG = "ADD USER ACTIVITY";
    private Button mAddUser;
    private EditText mAddFirstName;
    private EditText mAddLastName;
    private EditText mAddUserName;
    private EditText mAddEmail;
    private EditText mAddPassword;
    private EditText mAddGmailPassword;
    public ProgressDialog pd;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add User");


        mAddFirstName = (EditText) findViewById(R.id.txtAddFirstName);
        mAddLastName = (EditText) findViewById(R.id.txtAddLastName);
        mAddUserName = (EditText) findViewById(R.id.txtAddUserName);
        mAddEmail = (EditText) findViewById(R.id.inputEmail);
        mAddPassword = (EditText) findViewById(R.id.txtInputPass);
        mAddGmailPassword = (EditText) findViewById(R.id.txtInputGmailPass);


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
        pd = ProgressDialog.show(this, "Adding new user", "Please wait...");
        Users user = new Users(1, mAddFirstName.getText().toString(),mAddLastName.getText().toString(), mAddUserName.getText().toString(),mAddEmail.getText().toString(), "USER", mAddPassword.getText().toString(), mAddGmailPassword.getText().toString());
        int unesi;
        unesi = new AddNewUser(user).insertNewUser();
        pd.dismiss();
        if(unesi == 1){
            Toast.makeText(this, "New user added", Toast.LENGTH_LONG).show();
            finish();
        }else if(unesi == 2){
            Toast.makeText(this, "User with that username and email already exists", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "User isn't entered, try again", Toast.LENGTH_SHORT).show();
        }
    }
}
