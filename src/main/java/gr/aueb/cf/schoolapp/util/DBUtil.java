package gr.aueb.cf.schoolapp.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final BasicDataSource ds = new BasicDataSource();
    private static Connection connection;

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/schooldb7staging?serverTimeZone=UTC");
        ds.setUsername("user7");
        ds.setPassword(System.getenv("PASSWD_USER7"));
        ds.setInitialSize(10);
        ds.setMinIdle(10);
    }

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        connection = ds.getConnection();
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) connection.close();
    }
}
