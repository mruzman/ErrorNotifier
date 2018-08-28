package air.errornotifier;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import air.webservices.InsertUserInEmail;

public class ActivityResponse extends AppCompatActivity {

    private MainActivity mainActivity;
    private String titleText;
    private String descriptionText;
    private int emailID;
    private int userID;

    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnAccept;
    private Button btnDecline;

//    public ActivityResponse(MainActivity mainActivity, String titleText, String descriptionText, int emailID, int userID){
//        this.mainActivity = mainActivity;
//        this.titleText = titleText;
//        this.descriptionText = descriptionText;
//        this.emailID = emailID;
//        this.userID = userID;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_activity);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnDecline = (Button) findViewById(R.id.btnDecline);


        tvTitle = (TextView) findViewById(R.id.popupTitle);
        tvTitle.setText(titleText);

        tvDescription = (TextView) findViewById(R.id.popupDescription);
        tvDescription.setText(descriptionText);

        btnDecline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                try {
                    if(new InsertUserInEmail().execute(emailID, userID).get() == 1){
                        Toast.makeText(mainActivity,"You have taken the assignment", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mainActivity,"Somebody has already taken this assignment", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
