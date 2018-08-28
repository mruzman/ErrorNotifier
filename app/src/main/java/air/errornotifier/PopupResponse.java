package air.errornotifier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import air.database.Bean.Users;
import air.webservices.InsertUserInEmail;

/**
 * PopupResponse - popup koji se otvara ako je došao novi mail i čeka na interakciju korisnika
 *
 */
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

    /**
     * Kreiraj i pokaži popup
     *
     * @param view
     */
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
                    /**
                     * Postavljanje zastavice na true kako bi se znalo da je popup otvoren
                     *
                     */
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
            /**
             * Vibriraj prilikom otvaranja popup-a
             */
            Vibrator v = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long[] mVibratePattern = new long[]{0, 400, 1000, 600, 1000, 800, 1000, 1000};
                v.vibrate(VibrationEffect.createWaveform(mVibratePattern,-1));
            }else{
                v.vibrate(500);
            }

        }

    }
}
