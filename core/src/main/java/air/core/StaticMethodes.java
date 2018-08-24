package air.core;

import java.util.List;
import java.util.concurrent.ExecutionException;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.Bean.Priority;
import air.webservices.GetAllUnhandeledEmails;
import air.webservices.GetUserApps;
import air.webservices.IsEmailStillUnsolved;
import air.webservices.getAdminApps;

public class StaticMethodes {
    public static List<App> getAdminApps(Integer userId) throws ExecutionException, InterruptedException {
        return new getAdminApps().execute(userId).get();
    }

    public static List<Priority> getUserApps(Integer userId) throws ExecutionException, InterruptedException {
        return new GetUserApps().execute(userId).get();
    }

    public static List<Email> getAllUnhandeledEmails(List<Priority> aplikacije) throws ExecutionException, InterruptedException {
        return new GetAllUnhandeledEmails().execute(aplikacije).get();
    }

    public static boolean isStilUnsolved(Email email) throws ExecutionException, InterruptedException {
        return new IsEmailStillUnsolved().execute(email.getEmailId()).get();
    }
}
