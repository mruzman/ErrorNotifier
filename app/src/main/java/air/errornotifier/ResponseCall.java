package air.errornotifier;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.io.Serializable;

public class ResponseCall implements  Response {

    public static MainActivity activity;
    private String titleText;
    private String descriptionText;
    private int emailID;
    private int userID;

    public ResponseCall(MainActivity activity, String titleText, String descriptionText, int emailID, int userID){
        this.activity = activity;
        this.titleText = titleText;
        this.descriptionText= descriptionText;
        this.emailID= emailID;
        this.userID = userID;
    }

    /**
     * Kreiranje novog activity-a za mogućnost odgovora na mail
     * @param view
     */
    @Override
    public void CreatePopup(View view) {
        Intent intent = new Intent(activity.getCurrentFocus().getContext(), ActivityResponse.class);
        intent.putExtra("title", titleText);
        intent.putExtra("desc", descriptionText);
        intent.putExtra("emailID", emailID);
        intent.putExtra("userID", userID);
        activity.startActivity(intent);
    }
}
