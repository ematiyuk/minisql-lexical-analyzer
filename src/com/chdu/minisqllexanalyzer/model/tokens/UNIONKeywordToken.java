/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a UNION keyword.
 *
 * @author Eugene Matiyuk
 */
public class UNIONKeywordToken extends AbstractToken {

    public UNIONKeywordToken() {
        setTokenName("UNION keyword");
    }

    @Override
    protected boolean checkWithRegExp(String lexeme) {
        int state = 1;      // initial state
        char inputChar;
        boolean flag = false;

        int lexLength = lexeme.length();

        for (int charsCount = 0; charsCount < lexLength; charsCount++) {
            inputChar = lexeme.charAt(charsCount);

            switch (state) {
                case 1: {
                    if (inputChar == 'U' || inputChar == 'u') {
                        state = 2;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 2: {
                    if (inputChar == 'N' || inputChar == 'n') {
                        state = 3;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 3: {
                    if (inputChar == 'I' || inputChar == 'i') {
                        state = 4;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 4: {
                    if (inputChar == 'O' || inputChar == 'o') {
                        state = 5;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 5: {
                    if (inputChar == 'N' || inputChar == 'n') {
                        if (charsCount == lexLength - 1) {
                            flag = true;
                        }
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
            }
        }
        return flag;
    }

    @Override
    protected boolean checkWithFiniteAutomaton(String lexeme) {
        return new RegExpChecker().check(lexeme, "^[Uu][Nn][Ii][Oo][Nn]$");
    }
}
