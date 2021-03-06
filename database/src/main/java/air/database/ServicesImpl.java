package air.database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import air.database.Bean.App;
import air.database.Bean.Email;
import air.database.helper.Constants;

/**
 * Created by mruzman on 15.11.2017..
 */

public class ServicesImpl implements Services {
    private static String TAG = "Services";

    private int emailID;
    private int appID;

    /**
     * Funkcija za izvršavanje SELECT upita
     *
     * @param query
     * @return byte[] podataka
     * @throws IOException
     */
    @Override
    public byte[] queryManipulation(String query) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String createURL = Constants.URL + "?q="+query;
        Log.i(TAG, query);
        URL url = new URL(createURL);
        Log.i(TAG, createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = null;
        try{
            in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + createURL);
            }
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, byteRead);
            }
            out.close();
        } finally {
            connection.disconnect();
        }
        return out.toByteArray();
    }

    /**
     * Funckcija za izvršavanje INSERT, UPDATE, DELETE funkcija
     *
     * @param query
     * @return 1 ako je uspješno
     * @throws IOException
     */
    @Override
    public Integer insertQueryManipulation(String query) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String createURL = Constants.URL + "?q="+query;
        Log.i(TAG, query);
        URL url = new URL(createURL);
        Log.i(TAG, createURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = null;
        try{
            in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + createURL);
            }
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, byteRead);
            }
            out.close();
        } finally {
            connection.disconnect();
        }
        return Integer.valueOf(out.toString());
    }

    /**
     * Autentikacija
     *
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public byte[] getLogin(String username, String password) throws IOException, JSONException {
        String query = "SELECT * FROM user ";
        query += "WHERE username='" + username + "' ";
        query += "AND password='" + password + "'";
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje aplikacija kojima je korisnik administrator
     *
     * @param userId
     * @return
     * @throws IOException
     */
    @Override
    public byte[] getAdminApps(Integer userId) throws IOException {
        String query = "SELECT * FROM application where user_id = '"+ userId +"'";
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje korisnika po korisnickom imenu i email-u
     *
     * @param username
     * @param email
     * @return
     * @throws IOException
     */
    @Override
    public byte[] getIfExistsUser(String username, String email) throws IOException {
        String query = "SELECT * FROM user ";
        query += "WHERE username='" + username + "' ";
        query += "OR email='" + email + "'";
        return queryManipulation(query);
    }

    /**
     * Unos novog korisnika
     *
     * @param strings
     * @return
     * @throws IOException
     */
    @Override
    public Integer insertNewUser(String... strings) throws IOException {
        String firstName = strings[0];
        String lastName = strings[1];
        String username = strings[2];
        String email = strings[3];
        String password = strings[4];
        String gmailPassword = strings[5];
        String query = "INSERT INTO user(user_id,first_name,last_name,";
        query += "username,email,password,type, gmail_password) VALUES( default,'";
        query += firstName + "','" + lastName + "','";
        query += username + "','" + email + "','";
        query += password + "','USER'" + ", '" + gmailPassword + "')";
        return insertQueryManipulation(query);
    }

    /**
     * Dohvaćanje aplikacije po nazivu
     *
     * @param appName
     * @return
     * @throws IOException
     */
    @Override
    public byte[] getIfExistsApp(String appName) throws IOException {
        String query = "SELECT * FROM application WHERE name='" + appName + "'";
        return queryManipulation(query);
    }

    /**
     * Provjera da li je korisnik već pridružen toj aplikaciji
     *
     * @param appID
     * @param userID
     * @return
     * @throws IOException
     */
    public byte[] getIfExistsAppUser(String appID, String userID) throws IOException {
        String query = "SELECT * FROM user_application WHERE application_id='" + appID + "' AND user_id = '"+userID+"'";
        String createURL = Constants.URL + "?q=" + query;
        return queryManipulation(query);
    }

    /**
     * Unos nove aplikacije
     *
     * @param app
     * @param userID
     * @return
     * @throws IOException
     */
    @Override
    public Integer insertNewApp(String app, String userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO application(name, user_id) ";
        query += "VALUES('" + app + "', '" + userID + "')";
        return insertQueryManipulation(query);
    }

    /**
     * Pridruživanje korisnika aplikaciji
     *
     * @param appID
     * @param userID
     * @return
     * @throws IOException
     */
    public Integer insertNewAppUser(String appID, String userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "INSERT INTO user_application(application_id, user_id) ";
        query += "VALUES('" + appID + "', '"+ userID +"')";
        return insertQueryManipulation(query);
    }

    /**
     * Makivanje korisnika iz neke aplikacije
     *
     * @param appID
     * @param userID
     * @return
     * @throws IOException
     */
    public Integer deleteAppUser(String appID, String userID) throws IOException {
        ByteArrayOutputStream out;
        String query = "DELETE FROM user_application WHERE application_id = '"+appID+"'AND user_id = '"+userID+"' ";
        return insertQueryManipulation(query);
    }

    /**
     * Unos novog mail-a
     *
     * @param mail
     * @param app
     * @param userID
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public List<String> insertNewRecivedBug(Email mail, App app, int userID) throws IOException, JSONException {

        List<String> newValuesToReturn = new ArrayList<String>();
        emailID = 0;
        String stringAppID = new String(getIfExistsApp(app.getName()));
        Log.i("APPID", stringAppID);
        JSONObject jsonObject = new JSONObject(stringAppID);
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        if (stringAppID.length() > 0 || !stringAppID.isEmpty()) {
            JSONObject object = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
            appID = object.getInt("application_id");
            String emailIDString = new String(insertNewMail(mail.getHeader(), mail.getDescription(), mail.getTimeEventOccured(), appID));
            Log.i("EMAILID", emailIDString);
            jsonObject = new JSONObject(emailIDString);
            jsonArray = jsonObject.getJSONArray("records");
            object= new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
            emailID = object.getInt("email_id");
            newValuesToReturn.add(String.valueOf(appID));
            newValuesToReturn.add(String.valueOf(emailID));
        } else {
            Log.i(TAG, "Ne postoji aplikacija");
        }
        return newValuesToReturn;
    }


    private byte[]  insertNewMail(String header, String s, Timestamp s1, int appID) throws IOException {
        String query = "INSERT INTO email(header, description, time_event_occured, status, application) ";
        query += "VALUES('"+header+"','" + s + "','" + s1 + "','Unsolved','" + appID + "')";
        insertQueryManipulation(query);
        return checkAndGetEmail(s1);
    }

    /**
     * Dohvati mail
     *
     * @param s1
     * @return
     * @throws IOException
     */
    private byte[] checkAndGetEmail(Timestamp s1) throws IOException {
        String query = "SELECT email_id FROM email ";
        query += "WHERE time_event_occured = '" + s1 +"' LIMIT 1;";
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje svih korisnika
     *
     * @return
     * @throws IOException
     */
    public byte[] getUsers() throws IOException {
        String query = "SELECT * FROM user";
        return queryManipulation(query);
    }

    public byte[] getApps(String userID) throws IOException {
        String query = "SELECT * FROM application WHERE user_id = '" + userID + "'";
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje mail-ova
     *
     * @param appID
     * @return
     * @throws IOException
     */
    public byte[] getEmails(String appID) throws IOException {
        String query = "SELECT e.*, u.first_name, u.last_name FROM email AS e LEFT JOIN user AS u ON u.user_id = e.user_id WHERE e.application = '"+appID+"' AND e.user_id IS NOT NULL";
         return queryManipulation(query);
    }

    /**
     * Dohvaćanje korisnika za pridruživanje nekoj aplikaciji
     *
     * @param appID
     * @return
     * @throws IOException
     */
    public byte[] getAppUsers(int appID) throws IOException {
        String query = "SELECT u.*, CASE WHEN ua.application_id IS NULL THEN 0 ELSE ua.application_id END AS application_id, " +
                "CASE WHEN ua.priority IS NULL THEN 0 ELSE ua.priority END AS priority " +
                "FROM user AS u LEFT JOIN user_application AS ua ON ua.user_id = u.user_id AND ua.application_id = '" + appID + "' WHERE " +
                "u.type = 'USER'";
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje aplikacija korisnika za koje prima obavijesti
     *
     * @param userID
     * @return
     * @throws IOException
     */
    public byte[] getUserApps(String userID) throws IOException {
        String query = "SELECT a.* FROM application AS a INNER JOIN user_application AS ua ON a.application_id = ua.application_id WHERE ua.user_id = '" + userID + "'";
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje prioriteta korisnika za odabranu aplikaciju
     *
     * @param userID
     * @param appID
     * @return
     * @throws IOException
     */
    @Override
    public byte[] getUserPriority(Integer userID, Integer appID) throws IOException {
        String query = "SELECT priority FROM user_application ";
        query += "WHERE user_id=" + userID + " AND application_id=" + appID + ";";
        return queryManipulation(query);
    }

    @Override
    public byte[] getUserApps(Integer userId) throws IOException{
        String query = "SELECT * FROM user_application WHERE user_id="+userId;
        return queryManipulation(query);
    }

    /**
     * Dohvaćanje nepreuzetih zadataka
     *
     * @param whereClause
     * @return
     * @throws IOException
     */
    @Override
    public byte[] getUnhandledEmails(String whereClause) throws IOException {
        String query = "SELECT * FROM email WHERE application "+whereClause+ "and user_id IS NULL";
        return queryManipulation(query);
    }

    @Override
    public byte[] getAllResponsesForProblem(String emailID) throws IOException {
        //TODO vratiti
        String query = "SELECT * FROM response";
        query += "WHERE  email=" + emailID + " ORDER BY date_respond;";
        return queryManipulation(query);
    }

    /**
     * Postavljanje prioriteta
     *
     * @param priority
     * @param userID
     * @param appID
     * @return
     * @throws IOException
     */
    public Integer insertPriority(Integer priority, Integer userID, Integer appID) throws IOException {
        String query = "UPDATE user_application SET priority = " + priority + " WHERE user_id = " + userID + " AND application_id = " + appID;
        return insertQueryManipulation(query);
    }

    /**
     * Proveravanje da li je netko već preuzeo zadataka
     *
     * @param emailID
     * @return
     * @throws IOException
     */
    public byte[] isStillUntaken(int emailID) throws IOException{
        String query = "SELECT COALESCE(user_id,0) as user_id,status FROM email WHERE email_id ="+emailID;
        return queryManipulation(query);
    }

    /**
     * Preuzimanje zadatka
     *
     * @param email
     * @param user
     * @return
     * @throws IOException
     */
    @Override
    public Integer insertUserInEmail(int email, int user) throws IOException {
        String query = "UPDATE email SET user_id="+user+" where email_id="+email;
        return insertQueryManipulation(query);
    }

    /**
     * Postavljanje zadatka kao završenog
     *
     * @param emailID
     * @return
     * @throws IOException
     */
    @Override
    public Integer setEmailAsSolved(int emailID) throws IOException {
        String query = "UPDATE email SET status='SOLVED', time_closed = NOW() WHERE email_id = " + emailID;
        return insertQueryManipulation(query);
    }
}

