package air.errornotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import air.core.AddNewApplication;
import air.core.Bean.App;

/**
 * Created by Mateo on 6.12.2017..
 */

public class AddApplicationActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private Button btnAddApplication;
    private EditText mName;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add new application");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = (EditText)findViewById(R.id.txtName);
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

        App app = new App(1, mName.getText().toString());
        int done = new AddNewApplication(app).insertApp();

        if(done == 1){
            Intent intent = new Intent(AddApplicationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
