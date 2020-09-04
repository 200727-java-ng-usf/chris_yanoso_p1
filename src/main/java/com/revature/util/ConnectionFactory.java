package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * connects to the sql database
 */

public class ConnectionFactory {

    private static ConnectionFactory connFactory = new ConnectionFactory();

    private ConnectionFactory() {
        super();
    }

    public static ConnectionFactory getInstance() {
        return connFactory;
    }

    public Connection getConnection() {

        Connection conn = null;

        try {

            // Force the JVM to load the PostGreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://java-ng-usf-200727.casmekiat9nm.us-east-1.rds.amazonaws.com:5432/postgres",
                    "project_app",
                    "revature");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("A problem with database connection has occurred");
        }

        if (conn == null) {
            throw new RuntimeException("Failed to establish connection.");
        }
        return conn;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}