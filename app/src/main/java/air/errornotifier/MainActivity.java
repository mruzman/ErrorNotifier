package air.errornotifier;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import air.core.Bean.Users;
import air.core.IncomeMessages.IncomingMail;
import air.webservices.EmailListener;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "U MAIN ACTIVITYU";

    private Users user;

    private Toolbar mTolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
 //   private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabUser,fabGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (Users) getIntent().getSerializableExtra("User");
        Log.i(TAG, user.getFirstName() + " "+ user.getLastName());
        /*try {
            readMails();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


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





    }

    private void readMails() throws Exception {
        IncomingMail incomingMail = new IncomingMail(user);
    }
}
