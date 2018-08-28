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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.AppUser;
import air.database.Bean.Email;
import air.database.Bean.Users;
import air.database.helper.Constants;
import air.webservices.GetListOfEmails;
import air.webservices.GetListOfUsers;
import air.webservices.InsertUserInEmail;
import air.webservices.SetEmailAsSolved;

public class EmailActivity extends AppCompatActivity {
    private static final int CONTEXT_MENU_SET = 1;
    private static final String TAG = "EmailActivity";
    private Toolbar mTolbar;
    private List<Email> emailList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmailViewAdapter emailViewAdapter;
    private int appID;
    private int emailID;
    private int userID;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        int selectedApp = getIntent().getExtras().getInt("appid");
        final int userID = getIntent().getExtras().getInt("user_id");
        appID = selectedApp;
        mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar2);
        user = (Users) getIntent().getSerializableExtra("User");


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

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) throws ExecutionException, InterruptedException {
                return;
            }

            /**
             * Otvori menu nakon dugog pritiska na neki mail
             *
             * @param view
             * @param position
             */
            @Override
            public void onLongItemClick(View view, int position) {
                Email email = emailList.get(position);
                Log.i(TAG, "email: " + String.valueOf(email.getUserId()));
                Log.i(TAG, "user: " + String.valueOf(userID));
                Log.i(TAG, "status: " + email.getStatus());
                /**
                 * Otvori menu samo ako je prijavljeni korisnik taj koji je zadužen za rješvanje odabranog problema i ako zadatak nije riješen već
                 *
                 */
                if(email.getUserId() == userID && email.getStatus().equals("Unsolved")){
                    emailID = emailList.get(position).getEmailId();
                    registerForContextMenu(view);
                    openContextMenu(view);
                }
            }
        }));
        ApplicationsFragment.pd.dismiss();
    }

    /**
     * Otvaranje menu-a onLongClick
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Menu");
        menu.add(Menu.NONE, CONTEXT_MENU_SET, Menu.NONE, "Set as solved");

    }

    /**
     * Izvršavanje nakon što je korisnik pritisnuo da je riješio zadatak
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case CONTEXT_MENU_SET:
                try {
                    if(new SetEmailAsSolved().execute(emailID).get() == 1){
                        Toast.makeText(EmailActivity.this,"You have set assignment as solved", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EmailActivity.this,"You are not assigned to this assignment", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return true;
        }

        return true;
    }

    /**
     * Osvježi listu mail-ova nakon povratka na activity
     *
     */
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

    /**
     * Dohvaćanje svih mail-ova za odabranu aplikaciju
     *
     * @throws IOException
     * @throws JSONException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void getEmails() throws IOException, JSONException, ExecutionException, InterruptedException {
        emailList = (List<Email>) new GetListOfEmails().execute(String.valueOf(appID)).get();
        emailViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(!user.getType().equals(Constants.TYPE_ADMIN)){
            MenuItem item = menu.findItem(R.id.menu_settings);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure you want to log out ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent Logout = new Intent(EmailActivity.this, air.errornotifier.LoginActivity.class);
                        startActivity(Logout);

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            case R.id.menu_settings:
                Intent intent = new Intent(getApplicationContext(), ApplicationUsersActivity.class);
                intent.putExtra("appid", getIntent().getExtras().getInt("appid"));
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}
