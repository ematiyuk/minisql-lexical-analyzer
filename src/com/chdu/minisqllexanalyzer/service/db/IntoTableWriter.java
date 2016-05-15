/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.service.db;

import com.chdu.minisqllexanalyzer.service.ConsoleWriter;
import com.chdu.minisqllexanalyzer.service.utils.Constants;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * This class inserts into table using <code>TableModel</code> the results of
 * SQL statement execution.
 *
 * @author Eugene Matiyuk
 */
public class IntoTableWriter {

    /**
     * to print error messages into console
     */
    private final ConsoleWriter consoleWriter;

    public IntoTableWriter() {
        consoleWriter = new ConsoleWriter();
    }

    /**
     * Writes the data from <code>ResultSet</code> to the table model.
     *
     * @param model {@link DefaultTableModel} for interaction with table widget
     * @param query input SQL query string
     * @param forMsgComponent used in the <code>showMessageDialog</code> method
     * of class <code>JOptionPane</code>
     */
    public void write(DefaultTableModel model, String query, JComponent forMsgComponent) {
        try {
            SQLController controller = new SQLController(new DBConnection("org.sqlite.JDBC", "jdbc:sqlite:" + Constants.DB_File).getConnection());
            ResultSet resSet = controller.selectQuery(query);

            ResultSetMetaData resSetMD = resSet.getMetaData();

            // clear the table
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
            model.setColumnCount(0);

            // add names of columns to the table
            for (int colNum = 1; colNum <= resSetMD.getColumnCount(); colNum++) {
                model.addColumn(resSetMD.getColumnName(colNum));
            }

            while (resSet.next()) {

                ArrayList<String> row = new ArrayList<String>();

                for (int colNum = 1; colNum <= resSetMD.getColumnCount(); colNum++) {

                    int type = resSetMD.getColumnType(colNum);

                    switch (type) {

                        case Types.FLOAT:
                            row.add(Float.toString(resSet.getFloat(colNum)));
                            break;

                        case Types.INTEGER:
                            row.add(Integer.toString(resSet.getInt(colNum)));
                            break;

                        case Types.VARCHAR:
                            row.add(resSet.getString(colNum));
                            break;

                        default:
                            throw new Exception("Unsupported type.");
                    }
                }
                model.addRow(row.toArray());
            }

        } catch (SQLException exceptionSql) {
            consoleWriter.printErrorString(exceptionSql.toString());
            JOptionPane.showMessageDialog(forMsgComponent, exceptionSql.toString(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception exception) {
            consoleWriter.printErrorString(exception.toString());
            JOptionPane.showMessageDialog(forMsgComponent, "Something wrong with driver or/and database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
