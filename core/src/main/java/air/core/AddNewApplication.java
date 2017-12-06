package air.core;

import java.util.concurrent.ExecutionException;

import air.core.Bean.App;
import air.webservices.InsertNewApp;

/**
 * Created by Mateo on 6.12.2017..
 */

public class AddNewApplication {

    private static String TAG = "AddNewApplication";

    private App app;

    public AddNewApplication(App app) {
        this.app = app;
    }

    public int insertApp()throws ExecutionException, InterruptedException{

        if (new InsertNewApp().execute(app.getName()).get() == 1) {
            return 1;
        } else {
            return -1;
        }
    }
}
