package air.errornotifier;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import air.core.SetPriority;

public class SetPriorityActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private Button btnSetPriority;
    private int userID;
    private int appID;
    private EditText mPriority;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_priority);
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        userID = getIntent().getExtras().getInt("user_id");
        appID = getIntent().getExtras().getInt("app_id");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Set priority to user");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPriority = (EditText) findViewById(R.id.txtPriority);

        btnSetPriority = (Button)findViewById(R.id.btnSetPriority);

        btnSetPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    setPriority();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        });
    }

    private void setPriority() throws ExecutionException, InterruptedException {
        int done = new SetPriority(Integer.valueOf(mPriority.getText().toString()), userID, appID).set();
        if(done == 1){
            Toast.makeText(this, "Priority has been set successfully!", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "There was a problem while setting the priority, please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
