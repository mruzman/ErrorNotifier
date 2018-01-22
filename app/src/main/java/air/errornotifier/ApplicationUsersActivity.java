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

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.AppUser;
import air.database.Bean.Users;
import air.database.helper.Constants;
import air.webservices.GetListOfAppUsers;
import air.webservices.GetListOfApps;
import air.webservices.GetListOfUserApps;

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

        Log.i("App id: ", String.valueOf(appId));
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        uAdapter = new AppUsersViewAdapter(appsList, appId);
        try {
            getAppUsers();
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

        /*recyclerView.addOnItemTouchListener (new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AppUser selectedApp = appsList.get(position);
                int id = selectedApp.getApplicationId();
                intent.putExtra("appid", id);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/

    }

    private void getAppUsers() throws IOException, JSONException, ExecutionException, InterruptedException {

        appsList = (List<AppUser>) new GetListOfAppUsers().execute().get();

        uAdapter.notifyDataSetChanged();
    }
}
