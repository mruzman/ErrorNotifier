package air.errornotifier;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.AppUser;
import air.database.Bean.Users;
import air.database.helper.Constants;
import air.webservices.DeleteAppUser;
import air.webservices.GetListOfAppUsers;
import air.webservices.GetListOfApps;
import air.webservices.GetListOfUserApps;
import air.webservices.InsertNewAppUser;

/**
 * Created by Harm on 22.1.2018..
 */

public class ApplicationUsersActivity extends AppCompatActivity{

    private Toolbar mTolbar;
    private List<AppUser> appsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppUsersViewAdapter uAdapter;
    private AppUser appUser;
    private int appId;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_users);
        mTolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        appId = getIntent().getExtras().getInt("appid");

        setSupportActionBar(mTolbar);
        getSupportActionBar().setTitle("Add Users to Application");

        Log.i("App id: ", String.valueOf(appId));
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        uAdapter = new AppUsersViewAdapter(appsList, appId);
        try {
            getAppUsers(appId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        uAdapter = new AppUsersViewAdapter(appsList, appId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(uAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) throws ExecutionException, InterruptedException {
                AppUser appUser = appsList.get(position);
                appUser.setApplicationId(appId);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

                if(checkBox.isChecked()){
                    deleteAppUser(appUser);
                    checkBox.setChecked(false);
                }else{
                    saveAppUser(appUser);
                    checkBox.setChecked(true);
                }
                Log.i("Application ID: ", String.valueOf(appUser.getApplicationId()));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    private void saveAppUser(AppUser appUser) throws ExecutionException, InterruptedException {
        new InsertNewAppUser().execute(String.valueOf(appUser.getApplicationId()), String.valueOf(appUser.getUserId())).get();
    }

    private void deleteAppUser(AppUser appUser) throws ExecutionException, InterruptedException {
        new DeleteAppUser().execute(String.valueOf(appUser.getApplicationId()), String.valueOf(appUser.getUserId())).get();
    }
    private void getAppUsers(int appID) throws IOException, JSONException, ExecutionException, InterruptedException {

        appsList = (List<AppUser>) new GetListOfAppUsers().execute(String.valueOf(appID)).get();

        uAdapter.notifyDataSetChanged();
    }


}
