/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.service.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class is used for executing a static SQL statement and returning the
 * results it produces.
 *
 * @author Eugene Matiyuk
 */
public class SQLController {

    private Connection con = null;
    private Statement stmt = null;

    public SQLController(Connection con) throws SQLException {
        this.con = con;
        this.stmt = this.con.createStatement();
    }

    /**
     * Executes the given SQL statement, which returns a single
     * <code>ResultSet</code> object.
     *
     * @param query an SQL statement to be sent to the database
     * @return a <code>ResultSet</code> object that contains the data produced
     * by the given query; never <code>null</code>
     * @throws SQLException if a database access error occurs
     */
    public ResultSet selectQuery(String query) throws SQLException {
        return stmt.executeQuery(query);
    }
}
