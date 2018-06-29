package air.errornotifier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Users;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mateo on 20.1.2018..
 */

public class AppsViewAdapter extends RecyclerView.Adapter<AppsViewAdapter.AppsViewHolder> {

    private List<App> appsList;
    private List<String> mImages = new ArrayList<>();

    public class AppsViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        public TextView app;

        public AppsViewHolder(View view){
            super(view);
            image = itemView.findViewById(R.id.image);
            app = (TextView) view.findViewById(R.id.appRow);
        }
    }

    public AppsViewAdapter(List<App> appsList, List<String> mImages){

        this.appsList = appsList;
        this.mImages = mImages;
    }

    @Override
    public AppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_row, parent, false);
        return new AppsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppsViewHolder holder, int position) {
        App app = appsList.get(position);
        holder.app.setText(app.getName());
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }
}
