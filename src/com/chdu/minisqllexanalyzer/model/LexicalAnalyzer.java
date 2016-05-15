/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model;

import com.chdu.minisqllexanalyzer.service.ConsoleWriter;
import com.chdu.minisqllexanalyzer.model.tokens.IdentifierToken;
import com.chdu.minisqllexanalyzer.model.tokens.ValueToken;
import com.chdu.minisqllexanalyzer.model.tokens.KeywordToken;
import com.chdu.minisqllexanalyzer.model.tokens.TableNameToken;
import com.chdu.minisqllexanalyzer.model.tokens.RelOperatorToken;
import com.chdu.minisqllexanalyzer.model.tokens.FieldsListToken;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;
import com.chdu.minisqllexanalyzer.service.utils.Constants.Keywords;

/**
 * This class describes the flow of the whole lexical analysis process.
 *
 * @author Eugene Matiyuk
 */
public class LexicalAnalyzer {

    private ArrayList<Lexeme> lexemesList;
    private static final int ERROR_STATE = 999;
    private final ConsoleWriter consoleWriter;

    public LexicalAnalyzer(String query) {
        lexemesList = new ArrayList<Lexeme>();
        consoleWriter = new ConsoleWriter();
        lexemesList = splitStringIntoLexemes(query);
    }

    /**
     * @param sourceString The source input string to split
     * @return The array of separate lexemes of {@link Lexeme} class
     */
    private ArrayList<Lexeme> splitStringIntoLexemes(String sourceString) {
        StringTokenizer tokenizerResult = new StringTokenizer(sourceString, " ',\"");
        String lexeme;
        ArrayList<Lexeme> lexemes = new ArrayList<Lexeme>();
        int firstPosition;
        int shift = 0;
        while (tokenizerResult.hasMoreTokens()) {
            lexeme = tokenizerResult.nextToken();
            firstPosition = sourceString.indexOf(lexeme, shift) + 1;
            lexemes.add(new Lexeme(lexeme, firstPosition));
            shift = firstPosition + lexeme.length();
        }
        return lexemes;
    }

    /**
     * Checks whether input keyword exists in the lexemes list starting from
     * <code>beginIndex</code> parameter.
     *
     * @param keyword The keyword to check
     * @param beginIndex Starting from index
     * @return true if <code>keyword</code> exists
     */
    private boolean keywordExists(String keyword, int beginIndex) {
        for (Lexeme lexeme : lexemesList) {
            if (lexeme.getFirstSymbolPosition() >= beginIndex) {
                if (lexeme.getContents().compareToIgnoreCase(keyword) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Performs lexical analysis of the input <code>query</code> string.
     *
     * @param query The string to analyze
     * @param lexemesList The list of separate lexemes created beforehand
     * @param useRegExp If <code>true</code>, the analyzer uses regular
     * expressions for checking each lexeme as a certain token,
     * <code>false</code> - it uses finite automata
     * @return <code>true</code> if input query doesn't contain any mistakes
     */
    private boolean check(String query, ArrayList<Lexeme> lexemesList, boolean useRegExp) {
        consoleWriter.printCheckHeaderMsg(useRegExp);

        String notEnoughWordsError = "Not enough words in the query!";
        String mustNotLeaveAnythingAtTheEndError = "There must not be anything in the end!";
        String fromKeywordMissing = "FROM keyword misses or contains a mistake!";

        int state = 0;
        int lexCount = 0;
        boolean loopFlag = true;
        boolean returnFlag = false;
        String fieldsString; // contains all field IDs between SELECT and FROM
        Lexeme currentLexeme = lexemesList.get(lexCount);
        KeywordToken selectKeywordToken = new KeywordToken(Keywords.SELECT);
        KeywordToken fromKeywordToken = new KeywordToken(Keywords.FROM);
        KeywordToken whereKeywordToken = new KeywordToken(Keywords.WHERE);
        KeywordToken unionKeywordToken = new KeywordToken(Keywords.UNION);
        FieldsListToken fieldsListToken = new FieldsListToken();
        TableNameToken tableNameToken = new TableNameToken();
        IdentifierToken identifierToken = new IdentifierToken();
        RelOperatorToken relOperatorToken = new RelOperatorToken();
        ValueToken valueToken = new ValueToken();

        int lexListLength = lexemesList.size();

        do {
            switch (state) {
                // checks for SELECT keyword
                case 0: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (selectKeywordToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                selectKeywordToken.toString());

                        // checks if FROM keyword exists in the query, after SELECT
                        if (!this.keywordExists(Keywords.FROM.getContents(),
                                currentLexeme.getFirstSymbolPosition())) {
                            consoleWriter.printErrorString(fromKeywordMissing);
                            state = ERROR_STATE;
                        } else {
                            state = 1;
                            lexCount++;
                        }
                    } else {
                        consoleWriter.printKeywordErrorMsg(currentLexeme,
                                selectKeywordToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // checks for fields list tokens
                case 1: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else {
                        Lexeme firstLexeme = currentLexeme;

                        int fieldsListBeginIndex = firstLexeme.getFirstSymbolPosition() - 1;
                        int fieldsListEndIndex = query.toUpperCase(Locale.ENGLISH).
                                indexOf(Keywords.FROM.getContents(),
                                        fieldsListBeginIndex); // contains the first symbol position of the keyword FROM 

                        fieldsString = query.substring(fieldsListBeginIndex, fieldsListEndIndex);

                        if (fieldsListToken.check(fieldsString, useRegExp)) {
                            // while there's not FROM keyword, get each identifier (field)
                            while (currentLexeme.getContents().compareToIgnoreCase(Keywords.FROM.getContents()) != 0) {
                                consoleWriter.printLexemeDescription(currentLexeme,
                                        identifierToken.toString());
                                lexCount++; // go to the next lexeme
                                currentLexeme = lexemesList.get(lexCount);
                            }
                            state = 2;
                        } else {
                            consoleWriter.printTokenErrorMsg(currentLexeme,
                                    fieldsListToken.getTokenName());
                            state = ERROR_STATE;
                        }
                    }
                }
                break;
                // prints FROM keyword description
                case 2: {
                    consoleWriter.printLexemeDescription(currentLexeme,
                            fromKeywordToken.toString());
                    state = 3;
                    lexCount++;
                }
                break;
                // checks Table Name token
                case 3: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (tableNameToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                tableNameToken.toString());
                        state = 4;
                        lexCount++;
                        if (lexCount == lexListLength) { // if it's end of query
                            state = 9; // go to transitional state
                        }
                    } else {
                        consoleWriter.printTokenErrorMsg(currentLexeme,
                                tableNameToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // checks WHERE keyword
                case 4: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (whereKeywordToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                whereKeywordToken.toString());
                        state = 5;
                        lexCount++;
                    } else {
                        consoleWriter.printKeywordErrorMsg(currentLexeme,
                                whereKeywordToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // checks Identifier token
                case 5: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (identifierToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                identifierToken.toString());
                        state = 6;
                        lexCount++;
                    } else {
                        consoleWriter.printTokenErrorMsg(currentLexeme,
                                identifierToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // checks Relational Operator token
                case 6: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (relOperatorToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                relOperatorToken.toString());
                        state = 7;
                        lexCount++;
                    } else {
                        consoleWriter.printTokenErrorMsg(currentLexeme,
                                relOperatorToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // checks Value token
                case 7: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (valueToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                valueToken.toString());
                        state = 8;
                        lexCount++;
                        if (lexCount == lexListLength) { // if it's end of query
                            state = 9; // go to transitional state
                        }
                    } else {
                        consoleWriter.printTokenErrorMsg(currentLexeme,
                                valueToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // checks for UNION keyword
                case 8: {
                    if (lexCount == lexListLength) {
                        consoleWriter.printErrorString(notEnoughWordsError);
                        state = ERROR_STATE;
                    } else if (unionKeywordToken.check(currentLexeme.getContents(), useRegExp)) {
                        consoleWriter.printLexemeDescription(currentLexeme,
                                unionKeywordToken.toString());
                        state = 0; // go to initial state, for SELECT keyword checking
                        lexCount++;
                    } else {
                        consoleWriter.printKeywordErrorMsg(currentLexeme,
                                unionKeywordToken.getTokenName());
                        state = ERROR_STATE;
                    }
                }
                break;
                // transitional state
                case 9: {
                    if (lexCount == lexListLength) {
                        state = 10; // go to final state
                    } else {
                        state = ERROR_STATE;
                        consoleWriter.printErrorString(mustNotLeaveAnythingAtTheEndError);
                    }
                }
                break;
                case 10: {
                    returnFlag = true;
                    consoleWriter.printCorrectQueryMsg();
                    loopFlag = false;
                }
                break;
                case ERROR_STATE: {
                    consoleWriter.printIncorrrectQueryMsg();
                    loopFlag = false;
                }
                break;
            }
            if (lexCount < lexListLength) {
                currentLexeme = lexemesList.get(lexCount);
            }
        } while (loopFlag == true);
        return returnFlag;
    }

    /**
     * Performs lexical analysis of the input <code>query</code> string.
     *
     * @param query The string to analyze
     * @param useRegExp If <code>true</code>, the analyzer uses regular
     * expressions for checking each lexeme as a certain token,
     * <code>false</code> - it uses finite automata
     * @return <code>true</code> if input query doesn't contain any mistakes
     */
    public boolean checkQuery(String query, boolean useRegExp) {
        return check(query, lexemesList, useRegExp);
    }
}
