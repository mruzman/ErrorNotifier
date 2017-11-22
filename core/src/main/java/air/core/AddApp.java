package air.core;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import air.core.Bean.App;
import air.database.ServicesImpl;
import air.database.helper.Constants;

/**
 * Created by mruzman on 21.11.2017..
 */

public class AddApp extends AsyncTask<String, String, Integer> {
    private static String TAG = "AddApp";
    private static Integer CHECK = 0;
    private static Integer WRITE = 1;

    private App app;

    public AddApp(App app) {
        this.app = app;
    }

    public void insertAppInDB() throws ExecutionException, InterruptedException {
        int checkIfAppExists = checkIfAppAlreadyExists();
        Log.i(TAG, Integer.toString(checkIfAppExists));
        if (checkIfAppExists == -1) {
            Log.i(TAG, "Bačen je exception");
        } else if (checkIfAppExists == 0) {
            Log.i(TAG, "Aplikacija postoji zapisana u bazi");
        } else {
            Log.i(TAG, "Nema takve applikacije i zapisujem!");
            int write = writeAppInDatabase();
            if (write == 1) {
                Log.i(TAG, "Uspjesno zapisano!");
            } else {
                Log.i(TAG, "Greska prilikom upisivanja!");
            }
        }
    }

    private Integer checkIfAppAlreadyExists() throws ExecutionException, InterruptedException {
        return this.execute(String.valueOf(AddApp.CHECK), app.getName()).get();
    }

    private Integer writeAppInDatabase() throws ExecutionException, InterruptedException {
        int value = new AddApp(app).execute(String.valueOf(AddApp.WRITE), app.getName()).get();
        Log.i(TAG, String.valueOf(value));
        return value;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        int typeEvent = Integer.parseInt(strings[0]);
        if (typeEvent == AddApp.CHECK) {
            try {
                String appName = strings[1];
                Integer result = new ServicesImpl().getIfExistsApp(appName);

                if (result == Constants.DATA_DOESNT_EXISTS_IN_DATABASE) {
                    Log.i(TAG, "Ne postoji app s tim imenom");
                    return Constants.DATA_DOESNT_EXISTS_IN_DATABASE;
                } else if (result == Constants.DATA_EXISTS_IN_DATABASE) {
                    Log.i(TAG, "Aplikacija s tim imenom postoji!");
                    return Constants.DATA_EXISTS_IN_DATABASE;
                } else {
                    Log.i(TAG, "Pogreska prilikom uspostave konekcije");
                    return Constants.ERROR;
                }

            } catch (IOException ioe) {
                Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
            }
            return Constants.ERROR;
        } else {
            try {
                String appName = strings[1];
                Integer result = new ServicesImpl().insertNewApp(appName);
                if (result == Constants.DATA_SUCCESSFUL_IUD) {
                    Log.i(TAG, "Uneseno!");
                    return Constants.DATA_SUCCESSFUL_IUD;
                } else {
                    Log.i(TAG, "Pogreska prilikom uspostave konekcije");
                    return Constants.ERROR;
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Propao pokusaj dohvacanja: ", ioe);
            }
            return Constants.ERROR;
        }
    }


}
