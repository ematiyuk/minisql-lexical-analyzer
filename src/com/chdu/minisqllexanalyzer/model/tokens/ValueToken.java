/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;

/**
 * This token implementation defines a field value.
 *
 * @author Eugene Matiyuk
 */
public class ValueToken extends AbstractToken {

    private final String fieldValueAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "абвгдеєжзиіїйклмнопрстуфхцчшщьюяыёэАБВГДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯЫЁЭ"
            + "0123456789" + " ,-\"";

    public ValueToken() {
        setTokenName("Field Value");
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
        int state = 0;      // initial state
        char inputChar;

        int strLength = lexeme.length();

        for (int charsCount = 0; charsCount < strLength; charsCount++) {
            inputChar = lexeme.charAt(charsCount);

            switch (state) {
                case 0: {
                    if (isInAlphabet(inputChar, fieldValueAlphabet)) {
                        state = 0;
                    } else {
                        return false;
                    }
                }
                break;
            }
        }
        return true;
    }

    @Override
    public boolean checkWithRegExp(String lexeme) {
        return new RegExpChecker().check(lexeme, "^((0|([+-]?[1-9]\\d*)|(-?\\d+\\.\\d+))|([\\wА-Яа-я'-,\"\\sЄєІіЇї]+))$");
    }
}
