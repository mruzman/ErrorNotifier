package air.errornotifier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Users;

/**
 * Created by Mateo on 20.1.2018..
 */

public class AppsViewAdapter extends RecyclerView.Adapter<AppsViewAdapter.AppsViewHolder> {

    private List<App> appsList;

    public class AppsViewHolder extends RecyclerView.ViewHolder{

        public TextView app;

        public AppsViewHolder(View view){
            super(view);
            app = (TextView) view.findViewById(R.id.tvApplication);
        }
    }

    public AppsViewAdapter(List<App> appsList){

        this.appsList = appsList;
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
