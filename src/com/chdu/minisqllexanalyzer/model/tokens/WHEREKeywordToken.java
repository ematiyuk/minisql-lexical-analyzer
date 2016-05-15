/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a WHERE keyword.
 *
 * @author Eugene Matiyuk
 */
public class WHEREKeywordToken extends AbstractToken {

    public WHEREKeywordToken() {
        setTokenName("WHERE keyword");
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
                    if (inputChar == 'W' || inputChar == 'w') {
                        state = 2;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 2: {
                    if (inputChar == 'H' || inputChar == 'h') {
                        state = 3;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 3: {
                    if (inputChar == 'E' || inputChar == 'e') {
                        state = 4;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 4: {
                    if (inputChar == 'R' || inputChar == 'r') {
                        state = 5;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 5: {
                    if (inputChar == 'E' || inputChar == 'e') {
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
        return new RegExpChecker().check(lexeme, "^[Ww][Hh][Ee][Rr][Ee]$");
    }
}
