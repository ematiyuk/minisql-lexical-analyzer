/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a FROM keyword.
 *
 * @author Eugene Matiyuk
 */
public class FROMKeywordToken extends AbstractToken {

    public FROMKeywordToken() {
        setTokenName("FROM keyword");
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
                    if (inputChar == 'F' || inputChar == 'f') {
                        state = 2;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 2: {
                    if (inputChar == 'R' || inputChar == 'r') {
                        state = 3;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 3: {
                    if (inputChar == 'O' || inputChar == 'o') {
                        state = 4;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 4: {
                    if (inputChar == 'M' || inputChar == 'm') {
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
        return new RegExpChecker().check(lexeme, "^[Fr][Rr][Oo][Mm]$");
    }
}
