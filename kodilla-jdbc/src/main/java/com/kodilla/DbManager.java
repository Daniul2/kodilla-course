package com.kodilla;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {

    private static final String PROPS_FILE = "/db.properties";
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream is = DbManager.class.getResourceAsStream(PROPS_FILE)) {
            Properties props = new Properties();
            if (is != null) {
                props.load(is);
                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
            } else {
                url = "jdbc:mysql://localhost:3306/todo?useSSL=false&serverTimezone=UTC";
                user = "root";
                password = "qwerty12345@";
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
