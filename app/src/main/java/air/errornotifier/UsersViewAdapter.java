package air.errornotifier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import air.database.Bean.Users;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mateo on 19.1.2018..
 */

public class UsersViewAdapter extends RecyclerView.Adapter<UsersViewAdapter.UsersViewHolder> {

    private List<Users> usersList;
    private List<String> mImages = new ArrayList<>();

    public class UsersViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        public TextView user;

        public UsersViewHolder(View view){
            super(view);
            image = itemView.findViewById(R.id.image);
            user = (TextView) view.findViewById(R.id.user);

        }
    }

    public UsersViewAdapter(List<Users> usersList, List<String> mImages){

        this.usersList = usersList;
        this.mImages = mImages;

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
        return usersList.size();
    }
}
