/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.service.utils;

import java.util.Locale;

/**
 * Global constants class.
 *
 * @author Eugene Matiyuk
 */
public class Constants {

    public static final String DB_File = "./db/select_db.sqlite";

    public enum Keywords {

        SELECT("select"),
        FROM("from"),
        WHERE("where"),
        UNION("union");
        /* add new keywords here */

        private final String contents;

        private Keywords(String contents) {
            this.contents = contents.toUpperCase(Locale.ENGLISH);
        }

        public String getContents() {
            return contents;
        }
    }
}
