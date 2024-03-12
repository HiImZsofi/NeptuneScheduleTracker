package portfolio.sajat.nst.neptunescheduletracker.dev.tools.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnector {

    private String dbUser;
    private String dbPass;
    private String dbUrl;

    public DBConnector(@Value("${DB_USER}") String dbUser, @Value("${DB_PASSWORD}") String password) {
        this.dbUser = dbUser;
        this.dbPass = password;
        this.dbUrl = String.format("jdbc:mysql://localhost/?user=%s&password=%s", dbUser, dbPass);
    }

    public Connection createConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            throw new SQLException("Could not connect to database: " + e);
        }
    }
}


