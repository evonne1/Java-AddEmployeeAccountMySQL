
/**
 *  A java class  that list information about link to MySQL
 *
 * @author Yi fang Chen Date: 29/5/2018
 */

public class ConnectionDetails {

    private static final String userName = "root";  //add your own username
    private static final String passWord = "root"; //add your own password
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url
            = "jdbc:mysql://localhost:3306/login?autoReconnect=true";

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static String getDriver() {
        return driver;
    }

    public static String getUrl() {
        return url;
    }
}
