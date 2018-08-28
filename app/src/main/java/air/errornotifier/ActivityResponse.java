package air.errornotifier;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import air.webservices.InsertUserInEmail;

/**
 * Activity koji služi za prihvaćanje ili odbijanje preuzimanja novonastalog zadatka
 *
 */
public class ActivityResponse extends AppCompatActivity implements Serializable {

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
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long[] mVibratePattern = new long[]{0, 400, 1000, 600, 1000, 800, 1000, 1000};
            v.vibrate(VibrationEffect.createWaveform(mVibratePattern,-1));
        }else{
            v.vibrate(1000);
        }
        mainActivity = ResponseCall.activity;
        titleText = (String) getIntent().getSerializableExtra("title");
        descriptionText = (String) getIntent().getSerializableExtra("desc");
        emailID = (int) getIntent().getSerializableExtra("emailID");
        userID = (int) getIntent().getSerializableExtra("userID");

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnDecline = (Button) findViewById(R.id.btnDecline);


        tvTitle = (TextView) findViewById(R.id.popupTitle);
        tvTitle.setText(titleText);

        tvDescription = (TextView) findViewById(R.id.popupDescription);
        tvDescription.setText(descriptionText);

        /**
         * Pritiskom na tipku za odbijanje zadatka, zatvori activity
         *
         */
        btnDecline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CheckDatabase.isOpen = true;
                finish();
            }
        });

        /**
         * Pritiskom na tipku za prihvaćanje zadatka, zapiši u bazu user_id prijavljenog korisnika
         */
        btnAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                try {
                    CheckDatabase.isOpen = true;
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
