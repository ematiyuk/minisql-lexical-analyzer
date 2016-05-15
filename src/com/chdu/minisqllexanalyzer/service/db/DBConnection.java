/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.service.db;

import java.sql.*;

/**
 * Handle a connection to the database.
 *
 * @author Eugene Matiyuk
 */
public class DBConnection {

    private final String url;
    private final String driver;
    private Connection connection;

    /**
     * Initializes database connection parameters.
     *
     * @param driver driver class to load and register
     * @param url a database URL of the form
     * <code> jdbc:<em>subprotocol</em>:<em>subname</em></code>
     * @throws ClassNotFoundException if no definition for the driver class with
     * the specified string name in the {@link Class#forName(java.lang.String)}
     * could be found
     * @throws SQLException if a database access error occurs
     */
    DBConnection(String driver, String url) throws ClassNotFoundException, SQLException {
        this.driver = driver;
        this.url = url;
        Class.forName(this.driver);
        this.connection = DriverManager.getConnection(this.url);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
