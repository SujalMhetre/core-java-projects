package com.bank.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = DBConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (input != null) {
                Properties props = new Properties();
                props.load(input);
                URL = props.getProperty("db.url", "jdbc:mysql://localhost:3306/banking_system");
                USER = props.getProperty("db.user", "root");
                PASSWORD = props.getProperty("db.password", "Enter your password here");
            } else {
                // Fallback defaults
                URL = "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                USER = "root";
                PASSWORD = "Sujal@1234";
            }
        } catch (IOException e) {
            URL = "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            USER = "root";
            PASSWORD = "Sujal@1234";
        }
    }

   
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

   
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && conn.isValid(3);
        } catch (SQLException e) {
            return false;
        }
    }
}