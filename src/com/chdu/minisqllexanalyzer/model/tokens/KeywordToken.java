/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.Constants.Keywords;

/**
 * This token implementation defines all keywords. It does not check any keyword
 * here, it is a transitional token.
 *
 * @author Eugene Matiyuk
 */
public class KeywordToken extends AbstractToken {

    private Keywords keyword;

    public KeywordToken() {
        setTokenName("Keyword");
    }

    public KeywordToken(Keywords keyword) {
        setTokenName("Keyword");
        this.keyword = keyword;
    }

    @Override
    public boolean check(String lexeme, boolean useRegExp) {
        switch (keyword) {
            case SELECT: {
                return new SELECTKeywordToken().check(lexeme, useRegExp);
            }
            case FROM: {
                return new FROMKeywordToken().check(lexeme, useRegExp);
            }
            case WHERE: {
                return new WHEREKeywordToken().check(lexeme, useRegExp);
            }
            case UNION: {
                return new UNIONKeywordToken().check(lexeme, useRegExp);
            }
        }
        return false;
    }

    @Override
    protected boolean checkWithRegExp(String lexeme) {
        return false;
    }

    @Override
    protected boolean checkWithFiniteAutomaton(String lexeme) {
        return false;
    }
}
