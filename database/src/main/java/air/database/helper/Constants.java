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
}
