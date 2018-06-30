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
    public static Users user;
    private CheckMail checkMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (Users) getIntent().getSerializableExtra("User");
        checkMail();
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
    }


    //Dialog koji se treba otvoriti korisniku prilikom pojave greške u nekoj app
    List<String> openDialogResponse(String appName){
        ResponseDialog exampleDialog = new ResponseDialog(appName);
        //exampleDialog.setAppName(appName);
        exampleDialog.show(getSupportFragmentManager(), "Example dialog");
        while (exampleDialog.getOdgovori() == null || exampleDialog.getOdgovori().size() == 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    private void checkMail() {
        checkMail = new CheckMail(this);
        checkMail.start();
    }
}
