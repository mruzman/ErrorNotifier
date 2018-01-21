package air.errornotifier;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.Users;
import air.database.helper.Constants;
import air.webservices.GetListOfApps;
import air.webservices.GetListOfUserApps;
import air.webservices.GetListOfUsers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationsFragment extends Fragment {
    private List<App> appsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppsViewAdapter uAdapter;
    private Users user;

    public ApplicationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_applications, container, false);

        user = (Users) getActivity().getIntent().getSerializableExtra("User");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        uAdapter = new AppsViewAdapter(appsList);
        try {
            getApps();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        uAdapter = new AppsViewAdapter(appsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(uAdapter);

        recyclerView.addOnItemTouchListener (new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                App selectedApp = appsList.get(position);
                int id = selectedApp.getApplicationId();
                Intent intent = new Intent(getContext(), EmailActivity.class);
                intent.putExtra("appid", id);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        // Inflate the layout for this fragment
        return rootView;
    }

    private void getApps() throws IOException, JSONException, ExecutionException, InterruptedException {
        if(user.getType().equals(Constants.TYPE_ADMIN)){
            appsList = (List<App>) new GetListOfApps().execute().get();
        }else{
            appsList = (List<App>) new GetListOfUserApps().execute(String.valueOf(user.getUserId())).get();
        }

        uAdapter.notifyDataSetChanged();
    }
}
