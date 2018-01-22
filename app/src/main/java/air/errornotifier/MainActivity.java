package air.errornotifier;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.core.MailReader.MailResponse;
import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Event;
import air.database.Bean.Priority;
import air.database.Bean.Response;
import air.database.Bean.Users;
import air.core.MailReader.ReadMails;
import air.database.helper.Constants;
import air.webservices.GetListOfUsers;
import air.webservices.PriorityApp;


public class MainActivity extends AppCompatActivity implements ResponseDialog.ResponseDialogListener {

    private Toolbar mTolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    //   private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabUser, fabGroup;
    private Users user;
    private Email mail;
    private Priority priority;
    private Event event;
    private App app;
    private Response response;
    private String appName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (Users) getIntent().getSerializableExtra("User");

        try {
            checkMail();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (user.getType().equals(Constants.TYPE_ADMIN)) {

            //Dodavanje ActionBar-a
            mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
            setSupportActionBar(mTolbar);
            getSupportActionBar().setTitle("ErrorNotifier");

            //Tabs
            mViewPager = (ViewPager) findViewById(R.id.tabPager);
            SectionsPagerAdapter.countMenu = 2;
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            //ViewPager
            mViewPager.setAdapter(mSectionsPagerAdapter);

            //TabLayout
            mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
            mTabLayout.setupWithViewPager(mViewPager);


            //FloatingAction
            //      floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menuFab);
            fabUser = (FloatingActionButton) findViewById(R.id.menuFabAddUser);
            fabGroup = (FloatingActionButton) findViewById(R.id.menuFabAddApplication);


        } else {
            //Dodavanje ActionBar-a
            mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
            setSupportActionBar(mTolbar);
            getSupportActionBar().setTitle("ErrorNotifier");

            //Tabs
            mViewPager = (ViewPager) findViewById(R.id.tabPager);
            SectionsPagerAdapter.countMenu = 1;
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            //ViewPager
            mViewPager.setAdapter(mSectionsPagerAdapter);

            //TabLayout
            mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
            mTabLayout.setupWithViewPager(mViewPager);

            //FloatingAction
            fabUser = (FloatingActionButton) findViewById(R.id.menuFabAddUser);
            fabGroup = (FloatingActionButton) findViewById(R.id.menuFabAddApplication);
            FloatingActionMenu fabButton = (FloatingActionMenu) findViewById(R.id.menuFab);
            fabButton.setVisibility(View.GONE);

        }


//        floatingActionMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            Toast.makeText(MainActivity.this,"klik", Toast.LENGTH_SHORT).show();
//            }
//        });

        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addUserIntent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(addUserIntent);

                //  Toast.makeText(MainActivity.this,"korisnik",Toast.LENGTH_LONG).show();
            }
        });

        fabGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addAppIntent = new Intent(MainActivity.this, AddApplicationActivity.class);
                startActivity(addAppIntent);
                //Toast.makeText(MainActivity.this,"grupa",Toast.LENGTH_LONG).show();
            }
        });


        //Log.i("MAIN", user.getUsername().toString());

    }


    //Dialog koji se treba otvoriti korisniku prilikom pojave greške u nekoj app
    public List<String> openDialogResponse() throws InterruptedException {
        ResponseDialog exampleDialog = new ResponseDialog();
        //exampleDialog.setAppName(appName);
        exampleDialog.show(getSupportFragmentManager(), "Example dialog");
        while (exampleDialog.getOdgovori() == null || exampleDialog.getOdgovori().size() == 0) {
            Thread.sleep(2000);
        }
        return exampleDialog.getOdgovori();
    }

    //Metoda u kojoj se sprema odgovor iz responsa
    //Tu bi trebalo implementirati sto se dogada kada korisnik odabere hoce li rješavati problem ili ne
    @Override
    public void applyTexts(String resposne) {
        //textViewOdabir.setText(resposne);
        Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
    }

    //Dodavanje ikona u App Bar
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
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure you want to log out ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent Logout = new Intent(MainActivity.this, air.errornotifier.LoginActivity.class);
                        user = null;
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
                //ovdje će se pozvati settings
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = (MenuItem) menu.getItem(1);
        item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void checkMail() throws InterruptedException {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (user != null) {
                    try {
                        mail = null;
                        event = null;
                        app = null;
                        ReadMails readMails = new ReadMails(user);
                        List<Object> insertedEmails = readMails.checkIfNewEmailCame();
                        if (insertedEmails != null) {
                            for (Object object : insertedEmails) {
                                if (object instanceof Email) {
                                    mail = (Email) object;
                                }
                                if (object instanceof Event) {
                                    event = (Event) object;
                                    Log.i("U MAINU--", String.valueOf(event.getApplicationId()));
                                }
                                if (object instanceof App) {
                                    app = (App) object;
                                }
                                if (mail != null && event != null && app != null) {
                                    checkPriorityForApp();
                                    mail = null;
                                    event = null;
                                    app = null;
                                }
                            }

                        } else {
                            Log.i("MAIN_ZA_LISTU", "Prazna je lista EMAILOVA");
                        }
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void checkPriorityForApp() throws ExecutionException, InterruptedException {
        int priority = 0;
        if (event.getApplicationId() != 0) {
            priority = new PriorityApp().execute(user.getUserId(), event.getApplicationId()).get();
            Log.i("Prioritet", String.valueOf(priority));
            if (priority != 0) {
                if (priority == 1) {
                    response = new Response();
                    List<String> answer = openDialogResponse();
                    Log.i("ODGOVOR DOBIVENI", answer.get(1) + " " + answer.get(0));
                    if (answer.get(1) != "") {
                        response.setEmailId(mail.getEmailId());
                        response.setResponse(answer.get(1));
                        response.setUserId(user.getUserId());
                    }
                    mail.setStatus(answer.get(0));
                    MailResponse mailResponse = new MailResponse(mail, user, response);

                    if(response.getResponse() != "") {
                        mailResponse.insertAnswer(response.getResponse());
                    }
                    if(mail.getStatus() ==Constants.STATUS_IN_PROGRESS || mail.getStatus() ==Constants.STATUS_LATER){
                        mailResponse.insertNewStatus();
                    }
                    //response.insertAnswer("TU IDE ODGOVOR");
                } else if (priority == 2) {


                } else {
                    //radi ono zadnje xD
                }
            }
        }
    }
}
