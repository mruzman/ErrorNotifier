package air.database.helper;

/**
 * Created by mruzman on 3.11.2017..
 */

public class Constants {

    //KONSTANTE ZA DOHVAT PODATAKA
    public static final String URL = "https://error-notifier.000webhostapp.com//getData.php";
    public static final String HOST = "smtp.gmail.com";
    public static final String MAIL_STORE_TYPE = "smtp";

    //PODACI IZ BAZE
    public static final Integer DATA_DOESNT_EXISTS_IN_DATABASE = 1;
    public static final Integer DATA_EXISTS_IN_DATABASE = 0;

    //INSERT,UPDATE i DELETE --IUD is shorted from inserted updated deleted
    public static final Integer DATA_SUCCESSFUL_IUD = 1;
    public static final Integer DATA_FAILED_TO_IUD = 0;

    //ZA ERRORE!!
    public static final Integer ERROR = -1;


    public static final String ZABBIX_STRING = "Zabbix_problem";

    public static final String TYPE_USER = "USER";
    public static final String TYPE_ADMIN = "ADMIN";

    //ZA EMAILOVE
    public static final String STATUS_UNSOLVED = "Unsolved";
    public static final String STATUS_LATER = "Do that later";
    public static final String STATUS_IN_PROGRESS = "In progress";
    public static final String STATUS_FIXED = "Fixed";

    //PODACI IZ BAZE -- EMAIL
    public static final String EMAILID = "email_id";
    public static final String HEADER = "header";
    public static final String DESCRIPTION = "description";
    public static final String TIMEEVENTOCCURED = "time_event_occured";
    public static final String STATUS = "status";
    public static final String APPLICATION = "application";
    public static final String CLOSEDDESC = "closed_desc";
    public static final String TIMECLOSED = "time_closed";
    public static final String USERID = "user_id";
    //USER --UserID imamo
    public static final String FIRSTNAME="first_name";
    public static final String LASTNAME="last_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TYPE="type";
    public static final String GMAILPASS="gmail_password";
    //APPLICATION
    public static final String APPID = "application_id";
    public static final String NAME = "name";
    public static final String ADMIN = "admin";
    //USER_APPS --userID imamo, applicationID imamo
    public static final String USERAPP = "user_application_id";
    public static final String PRIORITY = "priority";


}
