/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a SELECT keyword.
 *
 * @author Eugene Matiyuk
 */
public class SELECTKeywordToken extends AbstractToken {

    public SELECTKeywordToken() {
        setTokenName("SELECT keyword");
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
                    if (inputChar == 'S' || inputChar == 's') {
                        state = 2;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 2: {
                    if (inputChar == 'E' || inputChar == 'e') {
                        state = 3;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 3: {
                    if (inputChar == 'L' || inputChar == 'l') {
                        state = 4;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 4: {
                    if (inputChar == 'E' || inputChar == 'e') {
                        state = 5;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 5: {
                    if (inputChar == 'C' || inputChar == 'c') {
                        state = 6;
                    } else {
                        charsCount = lexLength;
                    }
                }
                break;
                case 6: {
                    if (inputChar == 'T' || inputChar == 't') {
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
        return new RegExpChecker().check(lexeme, "^[Ss][Ee][Ll][Ee][Cc][Tt]$");
    }
}
