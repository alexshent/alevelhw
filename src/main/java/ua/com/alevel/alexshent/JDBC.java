package ua.com.alevel.alexshent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static Connection connection;

    private JDBC() {
    }

    public static synchronized Connection getConnection() {
        if (connection == null) {
            final String url = Configuration.getInstance().getProperty("url");
            final String user = Configuration.getInstance().getProperty("user");
            final String password = Configuration.getInstance().getProperty("password");
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
