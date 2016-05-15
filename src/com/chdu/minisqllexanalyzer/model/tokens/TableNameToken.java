/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a table name.
 *
 * @author Eugene Matiyuk
 */
public class TableNameToken extends AbstractToken {

    private final String tableNameAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final String firstSymbolTNameAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public TableNameToken() {
        setTokenName("Table Name");
    }

    private boolean isInAlphabet(char inputChar, String inputAlphabet) {
        int length = inputAlphabet.length();
        for (int i = 0; i < length; i++) {
            if (inputAlphabet.charAt(i) == inputChar) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkWithFiniteAutomaton(String lexeme) {
        int state = 1;      // initial state
        char inputChar;
        int lexLength = 8;      // expected lexeme length
        boolean flag = true;

        int strLength = lexeme.length();

        for (int charsCount = 0; charsCount < strLength; charsCount++) {
            inputChar = lexeme.charAt(charsCount);

            switch (state) {
                case 1: {
                    if (isInAlphabet(inputChar, firstSymbolTNameAlphabet)) {
                        state = 2;
                    } else {
                        flag = false;
                        charsCount = strLength;
                    }
                }
                break;
                case 2: {
                    if (charsCount >= lexLength) {
                        flag = false;
                        charsCount = strLength;
                    } else if (isInAlphabet(inputChar, tableNameAlphabet)) {
                        state = 2;
                    } else {
                        flag = false;
                        charsCount = strLength;
                    }
                }
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean checkWithRegExp(String lexeme) {
        return new RegExpChecker().check(lexeme, "^[a-zA-Z][a-zA-Z\\d]{0,7}$");
    }

}
