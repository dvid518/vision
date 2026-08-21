package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("CallToPrintStackTrace")

public class DBConnection {
    private static final String URL="jdbc:postgresql://localhost:5432/db_vision";
    private static final String USER="postgres";
    private static final String PASSWORD="123456";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}