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
    /**
     * Dohvačanje aplikacija kojima je korisnik administrator
     *
     * @param userId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static List<App> getAdminApps(Integer userId) throws ExecutionException, InterruptedException {
        return new getAdminApps().execute(userId).get();
    }

    /**
     * Dohvaćanje aplikacija za koje korisnik prima obavijesti
     *
     * @param userId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static List<Priority> getUserApps(Integer userId) throws ExecutionException, InterruptedException {
        return new GetUserApps().execute(userId).get();
    }

    /**
     * Dohvaćanje novih mail-ova
     *
     * @param aplikacije
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static List<Email> getAllUnhandeledEmails(List<Priority> aplikacije) throws ExecutionException, InterruptedException {
        return new GetAllUnhandeledEmails().execute(aplikacije).get();
    }

    /**
     * Provjera da li je već netko preuzeo zadatak
     *
     * @param email
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static boolean isStilUnsolved(Email email) throws ExecutionException, InterruptedException {
        return new IsEmailStillUnsolved().execute(email.getEmailId()).get();
    }
}
