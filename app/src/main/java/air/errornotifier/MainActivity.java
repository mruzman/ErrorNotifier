package air.errornotifier;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;


import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Event;
import air.database.Bean.Priority;
import air.database.Bean.Users;
import air.core.MailReader.ReadMails;
import air.webservices.PriorityApp;


public class MainActivity extends AppCompatActivity {

    private Toolbar mTolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
 //   private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabUser,fabGroup;
    private Users user;
    private Email mail = new Email();
    private Priority priority;
    private Event event= new Event();
    private App app;

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



        //Dodavanje ActionBar-a
        mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setTitle("ErrorNotifier");

        //Tabs
        mViewPager = (ViewPager) findViewById(R.id.tabPager);
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

    private void checkMail() throws InterruptedException {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (user.getEmail() != null || !user.getEmail().equals("")) {
                    try {
                        ReadMails readMails = new ReadMails(user);
                        List<Object> insertedEmails  = readMails.checkIfNewEmailCame();
                        if (insertedEmails != null) {
                            for(Object object : insertedEmails){
                                if(object instanceof Email){
                                    mail = (Email) object;
                                    Log.i("VRAĆENO U MAINU", String.valueOf(mail.getEmailId()));
                                }
                                if(object instanceof Event){
                                    event = (Event) object;
                                }
                                checkPriorityForApp();
                            }
                        }else{
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
        if(event.getApplicationId() != 0) {
            priority = new PriorityApp().execute(user.getUserId(), event.getApplicationId()).get();
            Log.i("Prioritet", String.valueOf(priority));
            if(priority!=0){
                if(priority == 1){
                    //radi ono kj je prioritet 1 pozovi
                }else if(priority == 2){
                    //radi ono kj je prioritet 2 notificiraj
                }else{
                    //radi ono zadnje xD
                }
            }
        }
    }
}
