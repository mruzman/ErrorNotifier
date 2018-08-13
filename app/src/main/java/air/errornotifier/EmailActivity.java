package air.errornotifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import air.database.Bean.Users;
import air.webservices.GetListOfEmails;
import air.webservices.GetListOfUsers;

public class EmailActivity extends AppCompatActivity {
    private Toolbar mTolbar;
    private List<Email> emailList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmailViewAdapter emailViewAdapter;
    private int appID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        int selectedApp = getIntent().getExtras().getInt("appid");
        appID = selectedApp;
        mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar2);

        setSupportActionBar(mTolbar);
        getSupportActionBar().setTitle("Emails");

        recyclerView = findViewById(R.id.recycler2);
        emailViewAdapter = new EmailViewAdapter(emailList);
        try {
            getEmails();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        emailViewAdapter = new EmailViewAdapter(emailList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(emailViewAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            getEmails();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        emailViewAdapter = new EmailViewAdapter(emailList);
    }

    private void getEmails() throws IOException, JSONException, ExecutionException, InterruptedException {
        emailList = (List<Email>) new GetListOfEmails().execute(String.valueOf(appID)).get();
        emailViewAdapter.notifyDataSetChanged();
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
            default:
                return true;
        }
    }
}
