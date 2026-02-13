package com.mfi.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sde2b_works";

    private static final String USER = "root";       // mysql username
    private static final String PASSWORD = "2006";   // mysql password

    public static Connection getDBConnection() throws Exception {

        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Create Connection
        Connection con = DriverManager.getConnection(
                URL, USER, PASSWORD
        );

        return con;
    }
}
