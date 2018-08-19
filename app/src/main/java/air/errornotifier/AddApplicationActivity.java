package air.errornotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.AddNewApplication;
import air.database.Bean.App;
import air.database.Bean.Users;
import air.webservices.GetListOfUsers;

/**
 * Created by Mateo on 6.12.2017..
 */

public class AddApplicationActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private Button btnAddApplication;
    private EditText mName;
    private Spinner mUserID;
    private static String TAG = "ADD APPLICATION ACTIVITY";
    List<Users> userList = new ArrayList<Users>();
    private List<String> stringUserList = new ArrayList<String>();
    Users user;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add new application");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = (EditText)findViewById(R.id.txtName);

        mUserID = (Spinner)findViewById(R.id.app_user_id);

        try {
            userList = (List<Users>) new GetListOfUsers().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        populateUserSpinner();


        btnAddApplication = (Button)findViewById(R.id.btnAddApplication);

        btnAddApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    addApplication();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        });
    }

    private void addApplication() throws ExecutionException, InterruptedException  {
        App app = new App(1, mName.getText().toString(), user.getUserId());
        int done = new AddNewApplication(app).insertApp();
        if(done == 1){
            Toast.makeText(this, "Application is successfully added", Toast.LENGTH_LONG).show();
            finish();
        }else if(done == 2){
            Toast.makeText(this, "Application with that name already exists", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "There was a problem while adding an application, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateUserSpinner(){
        for(Users user : userList){
            stringUserList.add(user.getFirstName().toString() + ' ' + user.getLastName().toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, stringUserList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserID.setAdapter(dataAdapter);
        mUserID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user = userList.get(i);
                Log.i(TAG, "User: " + user.getFirstName() + user.getLastName() + ", ID: " + user.getUserId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
