package air.errornotifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.Email;

public class EmailActivity extends AppCompatActivity {
    private Toolbar mTolbar;
    private List<Email> emailList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmailViewAdapter emailViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        int selectedApp = getIntent().getExtras().getInt("appid");
        //Log.i("position", "value" + selectedApp);
        mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setTitle("Emails");

        //
        recyclerView = findViewById(R.id.recycler2);
        emailViewAdapter = new EmailViewAdapter(emailList);
        /*try {
            //dohvati emailove
        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();*/

        emailViewAdapter = new EmailViewAdapter(emailList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(emailViewAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                builder.setTitle("Potvrda");
                builder.setMessage("Jeste li sigurni da se želite odjaviti?");
                builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent Logout = new Intent(EmailActivity.this, air.errornotifier.LoginActivity.class);
                        startActivity(Logout);

                    }
                });
                builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.menu_settings:
                //ovdje će se pozvati settings
                return true;
            default:
                return true;
        }
    }
}
