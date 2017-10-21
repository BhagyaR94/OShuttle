package application.constance;

import database.DatabaseHelperORM;

/**
 * Created by tharindu on 8/18/2017. olead
 * tharindup@orelit.com
 */
public class AppConstance {


    static String ERROR = "ERROR";
    static String WARNING = "WARNING";
    static String INFORMATION = "INFORMATION";
    static String DEBUG = "DEBUG";
    static String VERBOSE = "VERBOSE";

    public static String SHAREDPREFERENCES_USER_ID = "user_id";
    public static String appDbLocation =  "/data/com.orangeit.core.app/databases/"+ DatabaseHelperORM.DATABASE_NAME;
    public static boolean GPS_STATE=false;
    public static double factoryLatitude = 6.868651663190283;
    public static double factoryLogtitude = 80.06198644888059;


}
