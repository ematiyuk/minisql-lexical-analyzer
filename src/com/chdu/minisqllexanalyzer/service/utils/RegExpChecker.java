/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for regular expressions checking.
 *
 * @author Eugene Matiyuk
 */
public class RegExpChecker {

    /**
     * Checks whether input string matches input regular expression pattern.
     *
     * @param input input string to check
     * @param regExp input regular expression pattern
     * @return <code>true</code> if input string matches regular expression
     * pattern
     */
    public boolean check(String input, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
