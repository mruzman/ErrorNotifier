package air.errornotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import air.core.CheckSignUp;
import air.core.bean.UserBean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserBean user= new UserBean();
        user.setEmail("error.notifier.air@gmail.com");
        user.setPassword("airprojekterrornotifier");
        CheckSignUp signUp = new CheckSignUp(user);

    }

    //button je kliknut za prijavu...treba provjeriti na toj stranici da nije prazno!! imamo font-end
    // i backend provjere da nebi bilo nismo znali :)
    /** treba dodati metodu dok se klikne gumb
     * UserBean user= new UserBean();
     user.setEmail("error.notifier.air@gmail.com");
     user.setPassword("airprojekterrornotifier");
     CheckSignUp signUp = new CheckSignUp(user);
     if(signUp.isEmpty()){
     //tu ide alert da je prazno
     }
     */

}
