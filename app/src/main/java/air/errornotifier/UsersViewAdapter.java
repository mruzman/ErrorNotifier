package air.errornotifier;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import air.database.Bean.Users;

/**
 * Created by Mateo on 19.1.2018..
 */

public class UsersViewAdapter extends RecyclerView.Adapter<UsersViewAdapter.UsersViewHolder> {

    private List<Users> usersList;

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        public TextView user;

        public UsersViewHolder(View view){
            super(view);
            user = (TextView) view.findViewById(R.id.user);
        }
    }

    public UsersViewAdapter(List<Users> usersList){

        this.usersList = usersList;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_row, parent, false);
        return new UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        Users user = usersList.get(position);
        holder.user.setText(user.getFirstName() + ' ' + user.getLastName());
    }

    @Override
    public int getItemCount() {

        Log.i("broj usera", String.valueOf(usersList.size()));
        return usersList.size();
    }
}
