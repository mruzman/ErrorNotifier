package air.errornotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import air.webservices.GetJSON;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //GetJSON getJSON = new GetJSON("jbond", "jbond");
         //getJSON.getJSONValues();
    }
}
