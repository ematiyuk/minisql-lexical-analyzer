/*
 * MiniSQL Lexical Analyzer
 * Copyright(c) 2012 Eugene Matiyuk
 * Licensed under the MIT license
 */

package com.chdu.minisqllexanalyzer;

import com.chdu.minisqllexanalyzer.view.MainWindow;

/**
 * This class is entry point.
 *
 * @author Eugene Matiyuk
 */
public class Main {

    public static void main(String[] args) {
        MainWindow mainWin = new MainWindow();
        mainWin.setVisible(true);
    }
}
