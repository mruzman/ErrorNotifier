package air.errornotifier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import air.database.Bean.Users;
import air.webservices.InsertUserInEmail;

public class PopupResponse implements Response{
    private MainActivity mainActivity;
    private String titleText;
    private String descriptionText;
    private int emailID;
    private int userID;

    private Dialog myDialog;
    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnAccept;
    private Button btnDecline;

    public static Users user;

    public PopupResponse(MainActivity mainActivity, String titleText, String descriptionText, int emailID, int userID){
        this.mainActivity = mainActivity;
        this.titleText = titleText;
        this.descriptionText = descriptionText;
        this.emailID = emailID;
        this.userID = userID;
    }

    @Override
    public void CreatePopup(View view){
        myDialog = new Dialog(mainActivity);
        myDialog.setContentView(R.layout.popup);

        btnAccept = (Button) myDialog.findViewById(R.id.btnAccept);
        btnDecline = (Button) myDialog.findViewById(R.id.btnDecline);

        tvTitle = (TextView) myDialog.findViewById(R.id.popupTitle);
        tvTitle.setText(titleText);

        tvDescription = (TextView) myDialog.findViewById(R.id.popupDescription);
        tvDescription.setText(descriptionText);

        btnDecline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CheckDatabase.isOpen = true;
                myDialog.dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                try {
                    CheckDatabase.isOpen = true;
                    if(new InsertUserInEmail().execute(emailID, userID).get() == 1){
                        Toast.makeText(mainActivity,"You have taken the assignment", Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }else{
                        Toast.makeText(mainActivity,"Somebody has already taken this assignment", Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(!myDialog.isShowing()){
            myDialog.show();
        }

    }
}
