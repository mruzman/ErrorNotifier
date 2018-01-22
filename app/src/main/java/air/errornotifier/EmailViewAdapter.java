package air.errornotifier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import air.database.Bean.Email;

/**
 * Created by Marko Miletic on 22.1.2018..
 */

public class EmailViewAdapter extends RecyclerView.Adapter<EmailViewAdapter.EmailViewHolder> {

    private List<Email> emailList;

    public class EmailViewHolder extends RecyclerView.ViewHolder{

        public TextView email;

        public EmailViewHolder (View view){
            super(view);
            email = (TextView) view.findViewById(R.id.emailRow);
        }
    }

    public EmailViewAdapter(List<Email> emailList){
        this.emailList = emailList;
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_row, parent, false);
        return new EmailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (EmailViewHolder holder, int position){
        Email email = emailList.get(position);
        holder.email.setText(email.getDescription());
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }
}
