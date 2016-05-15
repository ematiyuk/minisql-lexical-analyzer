/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

/**
 * Encapsulates functionality and implementation that is common to all tokens of
 * input SQL-query.
 *
 * @author Eugene Matiyuk
 */
public abstract class AbstractToken {

    private String tokenName;

    public AbstractToken() {
        tokenName = "<No name>";
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * Method for lexeme check using regular expression.
     *
     * @param lexeme Input lexeme to check
     * @return <code>true</code> if input lexeme doesn't contain any mistakes
     */
    protected abstract boolean checkWithRegExp(String lexeme);

    /**
     * Method for lexeme check using finite automaton.
     *
     * @param lexeme Input lexeme to check
     * @return <code>true</code> if input lexeme doesn't contain any mistakes
     */
    protected abstract boolean checkWithFiniteAutomaton(String lexeme);

    /**
     * Common method for lexeme check.
     *
     * @param lexeme Input lexeme to check
     * @param useRegExp If <code>true</code>,
     * {@link #checkWithRegExp(java.lang.String)} is called for checking input
     * lexeme; if <code>false</code>,
     * {@link #checkWithFiniteAutomaton(java.lang.String)} is called instead
     * @return Execution result of one of check methods
     */
    public boolean check(String lexeme, boolean useRegExp) {
        return (useRegExp) ? checkWithRegExp(lexeme) : checkWithFiniteAutomaton(lexeme);
    }

    @Override
    public String toString() {
        return "token " + tokenName;
    }
}
