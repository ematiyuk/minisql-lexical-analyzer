/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model;

/**
 * This class is used for storing a substring (lexeme) got from the general
 * input string after tokenizing process.
 * <p>
 * It contains lexeme's contents, position of its first symbol in the input
 * string and length of the lexeme.
 *
 * @author Eugene Matiyuk
 */
public class Lexeme {

    private String contents;
    private int firstSymbolPosition;
    private int length;

    public Lexeme(String contents, int firstSymbolPosition) {
        this.contents = contents;
        this.firstSymbolPosition = firstSymbolPosition;
        this.length = contents.length();
    }

    public int getFirstSymbolPosition() {
        return firstSymbolPosition;
    }

    public int getLength() {
        return length;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setFirstSymbolPosition(int firstSymbolPosition) {
        this.firstSymbolPosition = firstSymbolPosition;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "> Lexeme \"" + this.contents + "\""
                + "\nInitial position: " + this.firstSymbolPosition
                + "\nLength: " + this.length
                + "\n";
    }
}
