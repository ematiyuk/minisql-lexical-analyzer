/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.service;

import com.chdu.minisqllexanalyzer.model.Lexeme;

/**
 * Messages writer into console.
 *
 * @author Eugene Matiyuk
 */
public class ConsoleWriter {

    public void printKeywordErrorMsg(Lexeme lexeme, String problemKeyword) {
        System.out.println("!>> Problem with " + problemKeyword + " in position "
                + lexeme.getFirstSymbolPosition() + "\nUnknown token \""
                + lexeme.getContents() + "\"\n");
    }

    public void printTokenErrorMsg(Lexeme lexeme, String problemToken) {
        System.out.println("!>> Problem with " + problemToken + " in position "
                + lexeme.getFirstSymbolPosition() + "\n");
    }

    public void printLexemeDescription(Lexeme lexeme, String tokenName) {
        System.out.println("> Lexeme \"" + lexeme.getContents() + "\" - " + tokenName
                + "\nInitial position: " + lexeme.getFirstSymbolPosition()
                + "\nLength: " + lexeme.getLength() + "\n");
    }

    public void printErrorString(String errorString) {
        System.out.println("!>> " + errorString + "\n");
    }

    public void printCorrectQueryMsg() {
        System.out.println("------------------------------------------------");
        System.out.println("+++ Query is CORRECT! +++");
        System.out.println("================================================" + "\n");
    }

    public void printIncorrrectQueryMsg() {
        System.out.println("------------------------------------------------");
        System.out.println("--- Query is INCORRECT! ---");
        System.out.println("================================================" + "\n");
    }

    public void printCheckHeaderMsg(boolean useRegExp) {
        System.out.println("************************************************");
        String checkTypeStr = (useRegExp) ? "Regular expression" : "Finite automaton";
        System.out.println("         " + checkTypeStr + " checking");
        System.out.println("************************************************" + "\n");
    }
}
