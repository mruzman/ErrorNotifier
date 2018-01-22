package air.errornotifier;


import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.Users;
import air.webservices.GetListOfUsers;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private List<Users> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UsersViewAdapter uAdapter;
    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        uAdapter = new UsersViewAdapter(usersList);
        try {
            getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        uAdapter = new UsersViewAdapter(usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(uAdapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        uAdapter = new UsersViewAdapter(usersList);
    }

    private void getUsers() throws IOException, JSONException, ExecutionException, InterruptedException {
        usersList = (List<Users>) new GetListOfUsers().execute().get();
        uAdapter.notifyDataSetChanged();
    }
}
