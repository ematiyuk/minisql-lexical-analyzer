/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a relational operator.
 *
 * @author Eugene Matiyuk
 */
public class RelOperatorToken extends AbstractToken {

    public RelOperatorToken() {
        setTokenName("Relational Operator");
    }

    @Override
    public boolean checkWithFiniteAutomaton(String lexeme) {
        int state = 0;      // initial state
        char inputChar;
        boolean flag = false;

        int strLength = lexeme.length();

        for (int charsCount = 0; charsCount < strLength; charsCount++) {
            inputChar = lexeme.charAt(charsCount);

            switch (state) {
                case 0: {
                    if (inputChar == '<') {
                        flag = true;
                        state = 1;
                    } else if (inputChar == '=') {
                        flag = true;
                    } else if (inputChar == '>') {
                        flag = true;
                        state = 6;
                    } else {
                        flag = false;
                    }
                }
                break;
                case 1: {
                    if (inputChar == '=') {
                        flag = true;
                    } else {
                        flag = inputChar == '>';
                    }
                }
                break;
                case 6: {
                    flag = inputChar == '=';
                }
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean checkWithRegExp(String lexeme) {
        return new RegExpChecker().check(lexeme, "^(<|>|=|<>|<=|>=){1}$");
    }
}
