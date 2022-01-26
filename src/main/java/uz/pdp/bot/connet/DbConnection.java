package uz.pdp.bot.connet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static final String URL="jdbc:postgresql://localhost:5432/fine";
    public static final String USERNAME="postgres";
    public static final String PASSWORD="5939523";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
