package portfolio.sajat.nst.neptunescheduletracker.dev.tools.database;

import org.springframework.beans.factory.annotation.Value;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    private static String dbUser;
    private static String dbPass;
    private static String dbUrl;

    @Value("${DB_USER}")
    public void setDbUser(String dbUser) {
        Connection.dbUser = dbUser;
    }

    @Value("${DB_PASSWORD}")
    public void setDbPass(String dbPass) {
        Connection.dbPass = dbPass;
    }

    @Value("${DB_URL}")
    public void setDbUrl(String dbUrl) {
        Connection.dbUrl = dbUrl;
    }

    public static java.sql.Connection createConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            throw new SQLException("Could not connect to database: " + e);
        }
    }
}


