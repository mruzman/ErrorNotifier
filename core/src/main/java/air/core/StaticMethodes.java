package air.core;

import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.webservices.GetUserApps;
import air.webservices.getAdminApps;

public class StaticMethodes {
    public static List<App> getAdminApps(Integer userId) throws ExecutionException, InterruptedException {
        return new getAdminApps().execute(userId).get();
    }

    public static List<Integer> getUserApps(Integer userId) throws ExecutionException, InterruptedException {
        return new GetUserApps().execute(userId).get();
    }
}
