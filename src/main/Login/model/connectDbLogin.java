package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDbLogin {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/schedule_app";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}