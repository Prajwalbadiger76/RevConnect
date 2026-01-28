package com.revconnect.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "revconnect";
    private static final String PASSWORD = "rev123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
