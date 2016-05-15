/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines an identifier.
 *
 * @author Eugene Matiyuk
 */
public class IdentifierToken extends AbstractToken {

    private final String fieldNameAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-0123456789";
    private final String firstSymbolFNameAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";

    private boolean isInAlphabet(char inputChar, String inputAlphabet) {
        int length = inputAlphabet.length();
        for (int i = 0; i < length; i++) {
            if (inputAlphabet.charAt(i) == inputChar) {
                return true;
            }
        }
        return false;
    }

    public IdentifierToken() {
        setTokenName("Identifier");
    }

    @Override
    public boolean checkWithFiniteAutomaton(String lexeme) {
        int state = 1;      // initial state
        char inputChar;
        int lexLength = 16;      // expected lexeme length
        boolean flag = true;

        int strLength = lexeme.length();

        for (int charsCount = 0; charsCount < strLength; charsCount++) {
            inputChar = lexeme.charAt(charsCount);

            switch (state) {
                case 1: {
                    if (isInAlphabet(inputChar, firstSymbolFNameAlphabet)) {
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
                    } else if (isInAlphabet(inputChar, fieldNameAlphabet)) {
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
        return new RegExpChecker().check(lexeme, "^[_a-zA-Z][a-zA-Z\\d_-]{0,15}$");
    }
}
