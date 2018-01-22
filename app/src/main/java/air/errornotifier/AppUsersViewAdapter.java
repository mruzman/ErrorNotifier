package air.errornotifier;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import air.database.Bean.AppUser;
import air.database.Bean.Users;

/**
 * Created by Mateo on 19.1.2018..
 */

public class AppUsersViewAdapter extends RecyclerView.Adapter<AppUsersViewAdapter.UsersViewHolder> {

    private List<AppUser> usersList;
    private int appId;

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        public CheckBox checkBox;

        public UsersViewHolder(View view){
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

    public AppUsersViewAdapter(List<AppUser> usersList, int appId){
        this.appId = appId;
        this.usersList = usersList;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_users_row, parent, false);
        return new UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        AppUser user = usersList.get(position);

        if(user.getApplicationId() == appId){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setText(user.getFirstName() + ' ' + user.getLastName());

    }

    @Override
    public int getItemCount() {
        Log.i("Count: ", String.valueOf(usersList.size()));
        return usersList.size();
    }

}
