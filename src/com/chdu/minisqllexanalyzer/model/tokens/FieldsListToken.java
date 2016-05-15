/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer.model.tokens;

import com.chdu.minisqllexanalyzer.service.utils.RegExpChecker;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This token implementation defines a fields list.
 *
 * @author Eugene Matiyuk
 */
public class FieldsListToken extends AbstractToken {

    public FieldsListToken() {
        setTokenName("Fields List");
    }

    /**
     * Checks commas equivalence in the input string of fields list.
     *
     * @param inputString String to check
     * @return <code>true</code> if commas number corresponds to Fields list one
     */
    private boolean checkCommas(String inputString) {
        boolean result = true;
        ArrayList<String> tokensList = new ArrayList<String>();
        StringTokenizer strTokenizer = new StringTokenizer(inputString, " ,( )\t\n\r");
        while (strTokenizer.hasMoreTokens()) {
            tokensList.add(strTokenizer.nextToken());
        }
        int commasCount = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == ',') {
                commasCount++;
            }
        }
        if (tokensList.size() - 1 != commasCount) {
            result = false;
            return result;
        }
        return result;
    }

    @Override
    public boolean checkWithFiniteAutomaton(String lexeme) {
        lexeme = lexeme.trim();
        boolean result = true;
        if (lexeme.compareTo("*") == 0) {
            return result;
        } else if (lexeme.endsWith(",") || lexeme.substring(0, 1).endsWith(",")) {
            return false;
        } else if (!checkCommas(lexeme)) {
            return false;
        } else {
            StringTokenizer lexemesList = new StringTokenizer(lexeme, ", ");
            IdentifierToken IDToken = new IdentifierToken();
            boolean useRegExp = false; // check with finite automaton
            while (lexemesList.hasMoreTokens()) {
                if (!IDToken.check(lexemesList.nextToken(), useRegExp)) {
                    return false;
                }
            }
        }
        return result;
    }

    @Override
    public boolean checkWithRegExp(String lexeme) {
        return new RegExpChecker().check(lexeme, "^((\\s*\\*\\s*)|((\\s*[_a-zA-Z][a-zA-Z\\d_-]{0,15}\\s*,\\s*)*\\s*[_a-zA-Z][a-zA-Z\\d_-]{0,15}\\s*))$");
    }
}
